/*
 * Copyright 2019-present CloudNetService team & contributors
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

package eu.cloudnetservice.ext.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComponentFormatsTest {

  @Test
  void testBasicConversion() {
    // Hello World
    var input = "&cHello &bW&6orld&r";

    var outputBungee = ComponentFormats.ADVENTURE_TO_BUNGEE.convertText(input);
    var outputAdventure = ComponentFormats.BUNGEE_TO_ADVENTURE.convertText(input);

    Assertions.assertEquals("§cHello §bW§6orld§r", outputBungee);
    Assertions.assertEquals("§cHello §bW§6orld§r", outputAdventure);
  }

  @Test
  void testHexFormatFromAdventureToBungee() {
    // Hello World
    var input = "&#084cfbH&#1a5ffbe&#2d71fbl&#3f84fcl&#5196fco &#64a9fcW&#76bbfco&#88cefdr&#9be0fdl&#adf3fdd";
    var bungee = "§x§0§8§4§c§f§bH§x§1§a§5§f§f§be§x§2§d§7§1§f§bl§x§3§f§8§4§f§cl§x§5§1§9§6§f§co "
      + "§x§6§4§a§9§f§cW§x§7§6§b§b§f§co§x§8§8§c§e§f§dr§x§9§b§e§0§f§dl§x§a§d§f§3§f§dd";

    var convertedToBungee = ComponentFormats.ADVENTURE_TO_BUNGEE.convertText(input);
    var convertedToAdventure = ComponentFormats.BUNGEE_TO_ADVENTURE.convertText(input);

    Assertions.assertEquals(bungee, convertedToBungee);
    Assertions.assertEquals(input.replace('&', '§'), convertedToAdventure);
  }

  @Test
  void testHexFormatFromBungeeToAdventure() {
    // Hello World
    var input = "&x&0&8&4&c&f&bH&x&1&a&5&f&f&be&x&2&d&7&1&f&bl&x&3&f&8&4&f&cl&x&5&1&9&6&f&co "
      + "&x&6&4&a&9&f&cW&x&7&6&b&b&f&co&x&8&8&c&e&f&dr&x&9&b&e&0&f&dl&x&a&d&f&3&f&dd";
    var adventure = "§#084cfbH§#1a5ffbe§#2d71fbl§#3f84fcl§#5196fco §#64a9fcW§#76bbfco§#88cefdr§#9be0fdl§#adf3fdd";

    var convertedToBungee = ComponentFormats.ADVENTURE_TO_BUNGEE.convertText(input);
    var convertedToAdventure = ComponentFormats.BUNGEE_TO_ADVENTURE.convertText(input);

    Assertions.assertEquals(adventure, convertedToAdventure);
    Assertions.assertEquals(input.replace('&', '§'), convertedToBungee);
  }

  @Test
  void testMiniMessageParsing() {
    // Test basic MiniMessage tags
    var input = "<red>Hello</red> <blue>World</blue>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageWithGradient() {
    // Test gradient tag
    var input = "<gradient:red:blue>Rainbow Text</gradient>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMixedLegacyAndMiniMessage() {
    // Test mixing legacy codes and MiniMessage tags
    var input = "&cLegacy red <blue>MiniMessage blue</blue> &aLegacy green";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageHoverAndClick() {
    // Test hover and click events
    var input = "<hover:show_text:'Hover text'><click:run_command:/test>Click me</click></hover>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageHexColors() {
    // Test MiniMessage hex colors
    var input = "<#FF5555>Red Text</#FF5555> <#5555FF>Blue Text</#5555FF>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testComplexMixedFormatting() {
    // Test complex mixing of both formats
    var input = "&l<red>Bold Red</red> &#FFAABBHex &n<blue>Underline Blue</blue>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageRainbow() {
    // Test rainbow tag
    var input = "<rainbow>Rainbow Text!</rainbow>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageReset() {
    // Test reset tag
    var input = "<red>Red <reset>Normal";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testLegacyOnlyStillWorks() {
    // Ensure legacy-only strings still work
    var input = "&a&lBold Green &c&oItalic Red &r&9Normal Blue";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }

  @Test
  void testMiniMessageOnlyWorks() {
    // Ensure MiniMessage-only strings work
    var input = "<green><bold>Bold Green</bold></green> <red><italic>Italic Red</italic></red>";
    var component = ComponentFormats.ADVENTURE.encodeStringToComponent(input);

    Assertions.assertNotNull(component);
  }
}
