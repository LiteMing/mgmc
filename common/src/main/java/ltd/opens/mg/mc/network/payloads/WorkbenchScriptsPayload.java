package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Server -> Client: Sends the updated list of bound blueprint scripts
 * after a BIND/UNBIND workbench action is processed.
 */
public record WorkbenchScriptsPayload(List<String> scripts) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "workbench_scripts");

    public static WorkbenchScriptsPayload decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        java.util.ArrayList<String> scripts = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scripts.add(buf.readUtf());
        }
        return new WorkbenchScriptsPayload(scripts);
    }

    public static void encode(FriendlyByteBuf buf, WorkbenchScriptsPayload payload) {
        buf.writeVarInt(payload.scripts().size());
        for (String script : payload.scripts()) {
            buf.writeUtf(script);
        }
    }
}
