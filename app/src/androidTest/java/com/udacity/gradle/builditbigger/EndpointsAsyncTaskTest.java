package com.udacity.gradle.builditbigger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;

@RunWith(JUnit4.class)
public class EndpointsAsyncTaskTest {
  @Test
  public void testJokeLoading() {
    final CountDownLatch signal = new CountDownLatch(1);
    new EndpointsAsyncTask(
            new EndpointsAsyncTask.JokeHandler() {
              @Override
              public void onJokeReady(String joke) {
                Assert.assertNotNull(joke);
                Assert.assertFalse(joke.isEmpty());
                signal.countDown();
              }
            })
        .execute("Yo yo");

    try {
      signal.await();
    } catch (InterruptedException e) {
      Assert.fail();
      e.printStackTrace();
    }
  }
}
