/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Android Sync Client.
 *
 * The Initial Developer of the Original Code is
 * the Mozilla Foundation.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * Jason Voll <jvoll@mozilla.com>
 * Richard Newman <rnewman@mozilla.com>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.mozilla.android.sync.repositories.domain;

import org.mozilla.android.sync.CryptoRecord;
import org.mozilla.android.sync.repositories.Utils;

/**
 * Covers the fields used by all bookmark objects.
 * @author rnewman
 *
 */
public class BookmarkRecord extends Record
                            implements SyncRecord {
  public BookmarkRecord(String guid, String collection, long lastModified) {
    super(guid, collection, lastModified);
  }
  public BookmarkRecord(String guid, String collection) {
    super(guid, collection, 0);
  }
  public BookmarkRecord(String guid) {
    super(guid, "bookmarks", 0);
  }
  public BookmarkRecord() {
    super(Utils.generateGuid(), "bookmarks", 0);
  }

  // Note: redundant accessors are evil. We're all grownups; let's just use
  // public fields.
  public long    androidID;
  public boolean loadInSidebar;
  public String  title;
  public String  bookmarkURI;
  public String  description;
  public String  tags;
  public String  keyword;
  public String  parentID;
  public String  parentName;
  public String  type;
  public String  generatorURI;
  public String  staticTitle;
  public String  folderName;
  public String  queryID;
  public String  siteURI;
  public String  feedURI;
  public String  pos;
  public String  children;

  @Override
  public void initFromPayload(CryptoRecord payload) {
    // TODO Auto-generated method stub
    // TODO Consider using Google's gson for parsing JSON into
    // these domain objects.    
  }

  @Override
  public CryptoRecord getPayload() {
    // TODO Auto-generated method stub
    return null;
  }
}
