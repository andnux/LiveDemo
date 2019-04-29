package top.andnux.liblive.pusher;

abstract class Pusher {

    volatile boolean isPushing = false;

    abstract void startPush();

    abstract void stopPush();

    abstract void release();
}
