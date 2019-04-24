/*
 * Copyright 2018-present Open Networking Foundation
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
 * limitations under the License.
 */
package io.atomix.core.value;

import java.util.ArrayList;
import java.util.List;

import io.atomix.primitive.config.ManagedPrimitiveConfig;

/**
 * Value primitive configuration.
 */
public abstract class ValueConfig<C extends ValueConfig<C>> extends ManagedPrimitiveConfig<C> {
  private Class<?> valueType;
  private List<Class<?>> extraTypes = new ArrayList<>();
  private boolean registrationRequired = false;
  private boolean compatibleSerialization = false;

  /**
   * Returns the value type.
   *
   * @return the value type
   */
  public Class<?> getValueType() {
    return valueType;
  }

  /**
   * Sets the value type.
   *
   * @param valueType the map value type
   * @return the value configuration
   */
  @SuppressWarnings("unchecked")
  public C setValueType(Class<?> valueType) {
    this.valueType = valueType;
    return (C) this;
  }

  /**
   * Returns the extra serializable types.
   *
   * @return the extra serializable types
   */
  public List<Class<?>> getExtraTypes() {
    return extraTypes;
  }

  /**
   * Sets the extra serializable types.
   *
   * @param extraTypes the extra serializable types
   * @return the value configuration
   */
  @SuppressWarnings("unchecked")
  public C setExtraTypes(List<Class<?>> extraTypes) {
    this.extraTypes = extraTypes;
    return (C) this;
  }

  /**
   * Adds an extra serializable type.
   *
   * @param extraType the extra type to add
   * @return the value configuration
   */
  @SuppressWarnings("unchecked")
  public C addExtraType(Class<?> extraType) {
    extraTypes.add(extraType);
    return (C) this;
  }

  /**
   * Returns whether registration is required for serializable types.
   *
   * @return whether registration is required for serializable types
   */
  public boolean isRegistrationRequired() {
    return registrationRequired;
  }

  /**
   * Sets whether registration is required for serializable types.
   *
   * @param registrationRequired whether registration is required for serializable types
   * @return the value configuration
   */
  @SuppressWarnings("unchecked")
  public C setRegistrationRequired(boolean registrationRequired) {
    this.registrationRequired = registrationRequired;
    return (C) this;
  }

  /**
   * Returns whether compatible serialization is enabled.
   *
   * @return whether compatible serialization is enabled
   */
  public boolean isCompatibleSerialization() {
    return compatibleSerialization;
  }

  /**
   * Sets whether compatible serialization is enabled.
   *
   * @param compatibleSerialization whether compatible serialization is enabled
   * @return the value configuration
   */
  @SuppressWarnings("unchecked")
  public C setCompatibleSerialization(boolean compatibleSerialization) {
    this.compatibleSerialization = compatibleSerialization;
    return (C) this;
  }
}