package org.wcx.dfs.datanode.server.server;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author wcx
 * @date 2023/5/4 16:18
 */
public class NameNodeOfferService {
    private NameNodeServiceActor activeServiceActor;
    private NameNodeServiceActor standbyServiceActor;
    private CopyOnWriteArrayList<NameNodeServiceActor> serviceActors;

    public NameNodeOfferService() {
        this.activeServiceActor = new NameNodeServiceActor();
        this.standbyServiceActor = new NameNodeServiceActor();

        this.serviceActors = new CopyOnWriteArrayList<NameNodeServiceActor>();
        this.serviceActors.add(activeServiceActor);
        this.serviceActors.add(standbyServiceActor);
    }

    public void start() {
        register();
        startHeartbeat();
    }

    private void register() {
        try {
            CountDownLatch latch = new CountDownLatch(2);
            this.activeServiceActor.register(latch);
            this.standbyServiceActor.register(latch);
            latch.await();
            System.out.println("已经向主备NameNode节点全部注册完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHeartbeat() {
        this.activeServiceActor.startHearbeat();
        this.standbyServiceActor.startHearbeat();
    }

    /**
     *
     * @param serviceActor
     */
    public void shutdown(NameNodeServiceActor serviceActor) {
        this.serviceActors.remove(serviceActor);
    }

    public void iterateServiceActors() {
        Iterator<NameNodeServiceActor> iterator = serviceActors.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

}
