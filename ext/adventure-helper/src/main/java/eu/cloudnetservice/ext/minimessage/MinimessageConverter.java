/*
 * Copyright 2019-2024 CloudNetService team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.cloudnetservice.ext.minimessage;

import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;

public class MinimessageConverter {
  protected static final char COLOR_CHAR = '§';
  protected static final char LEGACY_CHAR = '&';

  public static String convertToMinimessage(String input) {
    LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
      .character(LEGACY_CHAR)
      .extractUrls()
      .hexColors()
      .build();

    // Deserialize the legacy formatted string to a Component
    Component component = legacySerializer.deserialize(input.replace("§", "&"));

    // Serialize the Component to a MiniMessage formatted string
    String miniMessageString = MiniMessage.miniMessage().serialize(component);

    return miniMessageString.replace("\\", "");
  }

  public static String convertBungeeToMinimessageString(BaseComponent[] input) {
    val bungeeSerializer = BungeeComponentSerializer.get();
    Component component = bungeeSerializer.deserialize(input);
    return convertComponentToMinimessage(component);
  }

  public static String convertComponentToMinimessage(Component component) {
    return MiniMessage.miniMessage().serialize(component);
  }

  public static BaseComponent[] convertMinimessageStringToBungee(String input) {
    val miniMessageParser = MiniMessage.miniMessage();
    Component component = miniMessageParser.deserialize(convertToMinimessage(input));
    return convertComponentToBungee(component);
  }

  public static BaseComponent[] convertComponentToBungee(Component component) {
    val bungeeSerializer = BungeeComponentSerializer.get();
    return bungeeSerializer.serialize(component);
  }

  public static String convertToLegacyString(String input) {
    val miniMessageParser = MiniMessage.miniMessage();
    Component component = miniMessageParser.deserialize(input);
    return convertComponentToLegacyString(component);
  }

  public static String convertComponentToLegacyString(Component component) {
    val legacySerializer = LegacyComponentSerializer.builder()
      .character(LEGACY_CHAR)
      .character(COLOR_CHAR)
      .hexColors()
      .build();
    return legacySerializer.serialize(component);
  }
}
