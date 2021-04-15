/*
 * Copyright 2021 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.ssz.schema.collections.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import org.apache.tuweni.bytes.Bytes;
import tech.pegasys.teku.ssz.collections.SszByteList;
import tech.pegasys.teku.ssz.collections.impl.SszByteListImpl;
import tech.pegasys.teku.ssz.primitive.SszByte;
import tech.pegasys.teku.ssz.schema.SszPrimitiveSchemas;
import tech.pegasys.teku.ssz.schema.collections.SszByteListSchema;
import tech.pegasys.teku.ssz.schema.impl.AbstractSszListSchema;
import tech.pegasys.teku.ssz.tree.TreeNode;
import tech.pegasys.teku.ssz.tree.TreeUtil;

public class SszByteListSchemaImpl<SszListT extends SszByteList>
    extends AbstractSszListSchema<SszByte, SszListT> implements SszByteListSchema<SszListT> {

  public SszByteListSchemaImpl(long vectorLength) {
    super(SszPrimitiveSchemas.BYTE_SCHEMA, vectorLength);
  }

  @Override
  @SuppressWarnings("unchecked")
  public SszListT createFromBackingNode(TreeNode node) {
    return (SszListT) new SszByteListImpl(this, node);
  }

  @Override
  @SuppressWarnings("unchecked")
  public SszListT fromBytes(Bytes bytes) {
    return (SszListT) new SszByteListImpl(this, bytes);
  }

  public static TreeNode fromBytesToTree(SszByteListSchema<?> schema, Bytes bytes) {
    checkArgument(bytes.size() <= schema.getMaxLength(), "Bytes size greater than list max length");
    return SchemaUtils.createTreeFromBytes(bytes, schema.treeDepth());
  }

  public static Bytes fromTreeToBytes(SszByteListSchema<?> schema, TreeNode tree) {
    Bytes bytes = TreeUtil.concatenateLeavesData(tree);
    checkArgument(bytes.size() <= schema.getMaxLength(), "Tree doesn't match list schema");
    return bytes;
  }

  @Override
  public SszListT createFromElements(List<? extends SszByte> elements) {
    Bytes bytes = Bytes.of(elements.stream().mapToInt(sszByte -> 0xFF & sszByte.get()).toArray());
    return fromBytes(bytes);
  }
}
