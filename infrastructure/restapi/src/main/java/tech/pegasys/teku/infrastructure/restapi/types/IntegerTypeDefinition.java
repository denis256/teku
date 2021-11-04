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

package tech.pegasys.teku.infrastructure.restapi.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;

class IntegerTypeDefinition implements DeserializableTypeDefinition<Integer> {

  @Override
  public void serializeOpenApiType(final JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("type", "number");
    gen.writeEndObject();
  }

  @Override
  public void serialize(final Integer value, final JsonGenerator gen) throws IOException {
    gen.writeNumber(value);
  }

  @Override
  public Integer deserialize(final JsonParser parser) throws IOException {
    return parser.getIntValue();
  }
}