package org.mozilla.android.sync.test.helpers;

import org.mozilla.gecko.sync.Utils;
import org.mozilla.gecko.sync.delegates.ClientsDataDelegate;

public class MockClientsDataDelegate implements ClientsDataDelegate {
  private String accountGUID;
  private String clientName;
  private int clientsCount;

  @Override
  public synchronized String getAccountGUID() {
    if (accountGUID == null) {
      accountGUID = Utils.generateGuid();
    }
    return accountGUID;
  }

  @Override
  public synchronized String getClientName() {
    if (clientName == null) {
      clientName = "Default Name";
    }
    return clientName;
  }

  @Override
  public synchronized void setClientsCount(int clientsCount) {
    this.clientsCount = clientsCount;
  }

  @Override
  public synchronized int getClientsCount() {
    return clientsCount;
  }
}