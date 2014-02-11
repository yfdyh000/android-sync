/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.fxa.sync;

import org.mozilla.gecko.fxa.login.State.Action;
import org.mozilla.gecko.sync.BackoffHandler;

public interface SchedulePolicy {
  public abstract void onSuccessfulSync(int clientsCount);
  public abstract void onHandleFinal(Action needed);
  public abstract void onUpgradeRequired();
  public abstract void onUnauthorized();

  /**
   * Before a sync we typically wish to adjust our backoff policy. This cleans
   * the slate prior to encountering a new backoff, and also functions as a rate
   * limiter.
   *
   * The {@link SchedulePolicy} acts as a controller for the {@link BackoffHandler}.
   * As a result of calling these two methods, the {@link BackoffHandler} will be
   * mutated, and additional side-effects (such as scheduling periodic syncs) can
   * occur.
   *
   * @param backoffHandler the backoff handler to configure.
   */
  public abstract void configureBackoffMillisBeforeSyncing(BackoffHandler backoffHandler);
  public abstract void configureBackoffMillisOnBackoff(BackoffHandler backoffHandler, long backoffMillis);
}