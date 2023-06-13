/***************************************************************************************
 * Title: Testing the Un-Testable With Android Architecture Components - Room Queries
 * Author: Kapil Bakshi
 * Date: February 4, 2018
 * Code version: Java
 * Availability: https://proandroiddev.com/testing-the-un-testable-and-beyond-with-android-architecture-components-part-1-testing-room-4d97dec0f451
 *
 ***************************************************************************************/

package com.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {
    public static < T > T getValue(LiveData< T > liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer< T > observer = new Observer < T > () {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(10, TimeUnit.SECONDS);

        return (T) data[0];
    }
}

