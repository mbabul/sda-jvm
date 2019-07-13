package io.mbab.sda.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;


/**
 * Jeżeli chcesz, aby serwis działał w argumentach VM przy starcie aplikacji dodaj:
 * -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=9001
 * jeżeli port 9001 jest zajęty spróbuj inny
 */

@Component
@ManagedResource(objectName = "io.mbab.sda.jvm:name=JmxService")
public class JmxService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DataRepository repo = DataRepository.getInstance();

    @ManagedOperation
    public void deleteOne(int id) {
        repo.delete(id);
        log.info("Deleted DataModel object by JMX management, id: {}", id);
    }

    @ManagedOperation
    public void deleteAll() {
        repo.deleteAll();
        log.info("Deleted all DataModel object by JMX management");
    }
}
