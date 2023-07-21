//package org.wcx.dfs.datanode.server;
//
//import java.util.Iterator;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author wcx
// * @date 2023/5/4 16:18
// */
//public class NameNodeOfferService {
//    private NameNodeServiceActor serviceActor;
//    private CopyOnWriteArrayList<NameNodeServiceActor> serviceActors;
//
//    public NameNodeOfferService() {
//        this.serviceActor = new NameNodeServiceActor();
//    }
//
//    public void start() {
//        register();
//        startHeartbeat();
//    }
//
//    private void register() {
//        try {
//            this.serviceActor.register();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startHeartbeat() {
//        this.serviceActor.startHearbeat();
//    }
//
//    public void shutdown(NameNodeServiceActor serviceActor) {
//        this.serviceActors.remove(serviceActor);
//    }
//
//    public void iterateServiceActors() {
//        Iterator<NameNodeServiceActor> iterator = serviceActors.iterator();
//        while (iterator.hasNext()) {
//            iterator.next();
//        }
//    }
//
//}
