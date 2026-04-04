package ltd.opens.mg.mc.core.registry;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.registry.menu.MenuRegistry;
import ltd.opens.mg.mc.core.blueprint.inventory.BlueprintWorkbenchMenu;

public class MGMCRegistries {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = 
        DeferredRegister.create(MaingraphforMC.MODID, Registries.MENU);

    // 蓝图工作台菜单类型
    public static final RegistrySupplier<MenuType<BlueprintWorkbenchMenu>> BLUEPRINT_WORKBENCH_MENU = 
        MENU_TYPES.register("blueprint_workbench", () -> MenuRegistry.ofExtended(BlueprintWorkbenchMenu::new));

    public static void register() {
        MENU_TYPES.register();
    }
}
