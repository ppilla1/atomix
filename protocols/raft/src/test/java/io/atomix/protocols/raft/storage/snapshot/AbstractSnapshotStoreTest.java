/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package io.atomix.protocols.raft.storage.snapshot;

import io.atomix.protocols.raft.service.ServiceId;
import io.atomix.time.WallClockTimestamp;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Snapshot store test.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
@Test
public abstract class AbstractSnapshotStoreTest {

  /**
   * Returns a new snapshot store.
   */
  protected abstract SnapshotStore createSnapshotStore();

  /**
   * Tests writing a snapshot.
   */
  public void testWriteSnapshotChunks() {
    SnapshotStore store = createSnapshotStore();
    WallClockTimestamp timestamp = new WallClockTimestamp();
    Snapshot snapshot = store.newSnapshot(ServiceId.from(1), 2, timestamp);
    assertEquals(snapshot.serviceId(), ServiceId.from(1));
    assertEquals(snapshot.index(), 2);
    assertEquals(snapshot.timestamp(), timestamp);

    assertNull(store.getSnapshotById(ServiceId.from(1)));
    assertNull(store.getSnapshotByIndex(2));

    try (SnapshotWriter writer = snapshot.openWriter()) {
      writer.writeLong(10);
    }

    assertNull(store.getSnapshotById(ServiceId.from(1)));
    assertNull(store.getSnapshotByIndex(2));

    try (SnapshotWriter writer = snapshot.openWriter()) {
      writer.writeLong(11);
    }

    assertNull(store.getSnapshotById(ServiceId.from(1)));
    assertNull(store.getSnapshotByIndex(2));

    try (SnapshotWriter writer = snapshot.openWriter()) {
      writer.writeLong(12);
    }

    assertNull(store.getSnapshotById(ServiceId.from(1)));
    assertNull(store.getSnapshotByIndex(2));
    snapshot.complete();

    assertEquals(store.getSnapshotById(ServiceId.from(1)).serviceId(), ServiceId.from(1));
    assertEquals(store.getSnapshotById(ServiceId.from(1)).index(), 2);
    assertEquals(store.getSnapshotByIndex(2).serviceId(), ServiceId.from(1));
    assertEquals(store.getSnapshotByIndex(2).index(), 2);

    try (SnapshotReader reader = store.getSnapshotById(ServiceId.from(1)).openReader()) {
      assertEquals(reader.readLong(), 10);
      assertEquals(reader.readLong(), 11);
      assertEquals(reader.readLong(), 12);
    }
  }

}
