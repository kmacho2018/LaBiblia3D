package com.kmacho.labiblia3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by kmachoLaptop on 9/28/2017.
 */

public final class LoadBitmapTask implements Runnable {

    private final static String TAG = "LoadBitmapTask";
    private static LoadBitmapTask __object;

    final static int SMALL_BG = 0;
    final static int MEDIUM_BG = 1;
    final static int LARGE_BG = 2;
    final static int BG_COUNT = 10;

    int mBGSizeIndex;
    int mQueueMaxSize;
    int mPreRandomNo;
    boolean mIsLandscape;
    boolean mStop;
    Random mBGRandom;
    Resources mResources;
    Thread mThread;
    LinkedList<Bitmap> mQueue;
    int[][] mPortraitBGs;

    /**
     * Get an unique task object
     *
     * @param context Android context
     * @return unique task object
     */
    public static LoadBitmapTask get(Context context) {
        if (__object == null) {
            __object = new LoadBitmapTask(context);
        }
        return __object;
    }

    /**
     * Constructor
     *
     * @param context Android context
     */
    private LoadBitmapTask(Context context) {
        mResources = context.getResources();
        mBGRandom = new Random();
        mBGSizeIndex = SMALL_BG;
        mStop = false;
        mThread = null;
        mPreRandomNo = 0;
        mIsLandscape = false;
        mQueueMaxSize = 1;
        mQueue = new LinkedList<Bitmap>();

        // init all available bitmaps
        mPortraitBGs = new int[][]{
                new int[]{R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page},
                new int[]{R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page},
                new int[]{R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page, R.drawable.white_page, R.drawable.white_page,
                        R.drawable.white_page}
        };
    }

    /**
     * Acquire a bitmap to show
     * <p>If there is no cached bitmap, it will load one immediately</p>
     *
     * @return bitmap
     */
    public Bitmap getBitmap() {
        Bitmap b = null;
        synchronized (this) {
            if (mQueue.size() > 0) {
                b = mQueue.pop();
            }

            notify();
        }

        if (b == null) {
            Log.d(TAG, "Load bitmap instantly!");
            b = getRandomBitmap();
        }

        return b;
    }

    /**
     * Is task running?
     *
     * @return true if task is running
     */
    public boolean isRunning() {
        return mThread != null && mThread.isAlive();
    }

    /**
     * Start task
     */
    public synchronized void start() {
        if (mThread == null || !mThread.isAlive()) {
            mStop = false;
            mThread = new Thread(this);
            mThread.start();
        }
    }

    /**
     * Stop task
     * <p>Set mStop flag with true and notify task thread, at last, it will
     * check if task is alive every 500ms with 3 times to make sure the thread
     * stop</p>
     */
    public void stop() {
        synchronized (this) {
            mStop = true;
            notify();
        }

        // wait for thread stopping
        for (int i = 0; i < 3 && mThread.isAlive(); ++i) {
            Log.d(TAG, "Waiting thread to stop ...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }

        if (mThread.isAlive()) {
            Log.d(TAG, "Thread is still alive after waited 1.5s!");
        }
    }

    /**
     * Set bitmap width , height and maximum size of cache queue
     *
     * @param w         width of bitmap
     * @param h         height of bitmap
     * @param maxCached maximum size of cache queue
     */
    public void set(int w, int h, int maxCached) {
        int newIndex = LARGE_BG;
        if ((w <= 480 && h <= 854) ||
                (w <= 854 && h <= 480)) {
            newIndex = SMALL_BG;
        } else if ((w <= 800 && h <= 1280) ||
                (h <= 800 && w <= 1280)) {
            newIndex = MEDIUM_BG;
        }

        mIsLandscape = w > h;

        if (maxCached != mQueueMaxSize) {
            mQueueMaxSize = maxCached;
        }

        if (newIndex != mBGSizeIndex) {
            mBGSizeIndex = newIndex;
            synchronized (this) {
                cleanQueue();
                notify();
            }
        }
    }

    /**
     * Load bitmap from resources randomly
     *
     * @return bitmap object
     */
    private Bitmap getRandomBitmap() {
        int newNo = mPreRandomNo;
        while (newNo == mPreRandomNo) {
            newNo = mBGRandom.nextInt(BG_COUNT);
        }

        mPreRandomNo = newNo;
        int resId = mPortraitBGs[mBGSizeIndex][newNo];
        Bitmap b = BitmapFactory.decodeResource(mResources, resId);
        if (mIsLandscape) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap lb = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
                    matrix, true);
            b.recycle();
            return lb;
        }

        return b;
    }

    /**
     * Clear cache queue
     */
    private void cleanQueue() {
        for (int i = 0; i < mQueue.size(); ++i) {
            mQueue.get(i).recycle();
        }
        mQueue.clear();
    }

    public void run() {
        while (true) {
            synchronized (this) {
                // check if ask thread stopping
                if (mStop) {
                    cleanQueue();
                    break;
                }

                // load bitmap only when no cached bitmap in queue
                int size = mQueue.size();
                if (size < 1) {
                    for (int i = 0; i < mQueueMaxSize; ++i) {
                        Log.d(TAG, "Load Queue:" + i + " in background!");
                        mQueue.push(getRandomBitmap());
                    }
                }

                // wait to be awaken
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
