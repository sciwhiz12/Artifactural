/*
 * Artifactural
 * Copyright (c) 2018-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.artifactural.gradle;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ModifierAccess {
    private static Field MODIFIER_ACCESS = null;
    private static boolean accessAttempted = false;

    public static synchronized boolean definalize(Field target) {
        if ((target.getModifiers() & Modifier.FINAL) == 0) {
            return true;
        }

        if (MODIFIER_ACCESS == null && !accessAttempted) {
            try {
                final Field modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);
                MODIFIER_ACCESS = modifiers;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Could not access Field.modifiers to definalize reflection object. Use Java 8, current version: " + System.getProperty("java.version"), e);
            }
            accessAttempted = true;
        }
        if (MODIFIER_ACCESS != null) {
            try {
                MODIFIER_ACCESS.setInt(target, target.getModifiers() & ~Modifier.FINAL);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Could not definalize field " + target.getDeclaringClass().getName() + "." + target.getName(), e);
            }
            return true;
        }
        return false;
    }

}
