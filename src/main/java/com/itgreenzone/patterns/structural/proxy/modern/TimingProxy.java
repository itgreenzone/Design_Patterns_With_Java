package com.itgreenzone.patterns.structural.proxy.modern;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * MODERN VARIANT: a JDK DYNAMIC PROXY. One generic class adds timing/logging to
 * ANY interface at runtime – this is how Spring AOP creates proxies for
 * @Transactional and @Cacheable on interface-based beans.
 */
public final class TimingProxy {

    private TimingProxy() {
    }

    public static <T> T wrap(Class<T> iface, T target, List<String> log) {
        Object proxy = Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, (p, method, args) -> {
            long start = System.nanoTime();
            try {
                return method.invoke(target, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();                         // rethrow the real exception
            } finally {
                log.add(iface.getSimpleName() + "." + method.getName() + " took " + (System.nanoTime() - start) / 1000 + "µs");
            }
        });
        return iface.cast(proxy);
    }
}
