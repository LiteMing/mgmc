package ltd.opens.mg.mc.core.registry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlueprintItemHelper {
    public static final String TAG_KEY = "mgmc_scripts";

    public static List<String> getScripts(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_KEY, Tag.TAG_LIST)) return Collections.emptyList();
        ListTag list = tag.getList(TAG_KEY, Tag.TAG_STRING);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.getString(i));
        }
        return result;
    }

    public static boolean hasScripts(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TAG_KEY, Tag.TAG_LIST);
    }

    public static void setScripts(ItemStack stack, List<String> scripts) {
        if (scripts.isEmpty()) {
            removeScripts(stack);
            return;
        }
        ListTag list = new ListTag();
        for (String s : scripts) {
            list.add(StringTag.valueOf(s));
        }
        stack.getOrCreateTag().put(TAG_KEY, list);
    }

    public static void removeScripts(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove(TAG_KEY);
            if (tag.isEmpty()) {
                stack.setTag(null);
            }
        }
    }
}
