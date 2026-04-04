package ltd.opens.mg.mc.client.gui.screens;

import ltd.opens.mg.mc.client.gui.GuiCompat;
import ltd.opens.mg.mc.client.network.NetworkService;
import ltd.opens.mg.mc.core.blueprint.inventory.BlueprintWorkbenchMenu;
import ltd.opens.mg.mc.core.registry.BlueprintItemHelper;
import ltd.opens.mg.mc.network.payloads.WorkbenchActionPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class BlueprintWorkbenchScreen extends AbstractContainerScreen<BlueprintWorkbenchMenu> {

    private SimpleListWidget<LibraryItem> allBlueprintsList;
    private SimpleListWidget<String> boundBlueprintsList;

    public BlueprintWorkbenchScreen(BlueprintWorkbenchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 150;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
    }

    @Override
    protected void init() {
        super.init();

        int listWidth = 85;
        int listHeight = 90;
        int listY = this.topPos + 35;

        this.boundBlueprintsList = new SimpleListWidget<>(this.leftPos + 55, listY, listWidth, listHeight, 20,
            (graphics, item, x, y, w, h, hovered) -> renderBoundItem(graphics, item, x, y, w, h, hovered),
            this.font);
        this.addRenderableWidget(this.boundBlueprintsList);

        this.allBlueprintsList = new SimpleListWidget<>(this.leftPos + 160, listY, listWidth, listHeight, 20,
            (graphics, item, x, y, w, h, hovered) -> renderLibItem(graphics, item, x, y, w, h, hovered),
            this.font);
        this.addRenderableWidget(this.allBlueprintsList);

        this.addRenderableWidget(Button.builder(Component.literal("▶"), b -> {
            LibraryItem selected = allBlueprintsList.getSelected();
            if (selected != null) {
                NetworkService.getInstance().sendWorkbenchAction(WorkbenchActionPayload.Action.BIND, selected.path);
            }
        }).bounds(this.leftPos + 142, this.topPos + 65, 16, 20).build());

        NetworkService.getInstance().requestBlueprintList();
    }

    public void updateListFromServer(List<String> blueprints) {
        if (allBlueprintsList != null) {
            allBlueprintsList.clear();
            for (String bp : blueprints) {
                allBlueprintsList.add(new LibraryItem(bp));
            }
        }
    }

    public void updateBoundScriptsFromServer(List<String> scripts) {
        if (boundBlueprintsList != null) {
            if (scripts != null && !scripts.isEmpty()) {
                List<String> copy = new ArrayList<>(scripts);
                String selected = boundBlueprintsList.getSelected();
                boundBlueprintsList.clear();
                for (String s : copy) {
                    boundBlueprintsList.add(s);
                }
                if (selected != null) {
                    boundBlueprintsList.setSelected(selected);
                }
            } else {
                boundBlueprintsList.clear();
            }
        }
    }

    private ItemStack lastStack = ItemStack.EMPTY;
    private List<String> lastScripts = null;

    @Override
    public void containerTick() {
        super.containerTick();
        if (this.boundBlueprintsList != null) {
            ItemStack stack = this.menu.getTargetItem();
            List<String> scripts = stack.isEmpty() ? null : BlueprintItemHelper.getScripts(stack);
            if (scripts != null && scripts.isEmpty()) scripts = null;

            boolean changed = false;
            if (stack.isEmpty() != lastStack.isEmpty()) {
                changed = true;
            } else if (!stack.isEmpty()) {
                if (scripts == null && lastScripts != null) changed = true;
                else if (scripts != null && !scripts.equals(lastScripts)) changed = true;
            }

            if (changed) {
                if (scripts != null) {
                    this.updateBoundScriptsFromServer(scripts);
                } else {
                    this.boundBlueprintsList.clear();
                }
                lastStack = stack.copy();
                lastScripts = scripts == null ? null : List.copyOf(scripts);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        graphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, 0xFFC6C6C6);
        graphics.fill(x, y, x + this.imageWidth, y + 1, 0xFFFFFFFF);
        graphics.fill(x, y, x + 1, y + this.imageHeight, 0xFFFFFFFF);
        graphics.fill(x + this.imageWidth - 1, y, x + this.imageWidth, y + this.imageHeight, 0xFF555555);
        graphics.fill(x, y + this.imageHeight - 1, x + this.imageWidth, y + this.imageHeight, 0xFF555555);

        // 手持物品槽
        int slotX = x + 20;
        int slotY = y + 35;
        graphics.fill(slotX - 1, slotY - 1, slotX + 19, slotY + 19, 0xFF373737);
        graphics.fill(slotX, slotY, slotX + 19, slotY + 19, 0xFFFFFFFF);
        graphics.fill(slotX, slotY, slotX + 18, slotY + 18, 0xFF8B8B8B);

        ItemStack heldItem = this.menu.getTargetItem();
        if (!heldItem.isEmpty()) {
            graphics.renderItem(heldItem, slotX + 1, slotY + 1);
            graphics.renderItemDecorations(this.font, heldItem, slotX + 1, slotY + 1);
        } else {
            graphics.drawString(this.font, Component.translatable("gui.mgmc.workbench.empty"), slotX + 4, slotY + 5, 0xFF888888, false);
        }
        graphics.drawString(this.font, Component.translatable("gui.mgmc.workbench.held_item"), x + 10, y + 22, 0xFF404040, false);

        renderPanel(graphics, x + 55, y + 35, 85, 90, Component.translatable("gui.mgmc.workbench.bound_blueprints").getString());
        renderPanel(graphics, x + 160, y + 35, 85, 90, Component.translatable("gui.mgmc.workbench.blueprint_library").getString());

        graphics.drawString(this.font, this.title, x + 8, y + 8, 0xFF404040, false);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (boundBlueprintsList.isMouseOver(mouseX, mouseY) && boundBlueprintsList.mouseScrolled(delta)) return true;
        if (allBlueprintsList.isMouseOver(mouseX, mouseY) && allBlueprintsList.mouseScrolled(delta)) return true;
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void renderPanel(GuiGraphics g, int x, int y, int w, int h, String label) {
        g.fill(x - 1, y - 1, x + w, y + h, 0xFF373737);
        g.fill(x, y, x + w + 1, y + h + 1, 0xFFFFFFFF);
        g.fill(x, y, x + w, y + h, 0xFF8B8B8B);
        g.drawString(this.font, Component.literal(label), x, y - 10, 0xFF404040, false);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    private void renderItemBg(GuiGraphics g, int x, int y, int w, int h, boolean selected, boolean hovered) {
        if (selected) {
            g.fill(x, y, x + w, y + h, 0xFFFFFFFF);
        } else if (hovered) {
            g.fill(x, y, x + w, y + h, 0x44FFFFFF);
        }
    }

    private void renderLibItem(GuiGraphics g, LibraryItem item, int x, int y, int w, int h, boolean hovered) {
        boolean selected = item == allBlueprintsList.getSelected();
        renderItemBg(g, x, y, w, h, selected, hovered);
        g.drawString(minecraft.font, item.displayName(), x + 4, y + 5, selected ? 0xFF404040 : 0xFFFFFFFF, false);
    }

    private void renderBoundItem(GuiGraphics g, String path, int x, int y, int w, int h, boolean hovered) {
        renderItemBg(g, x, y, w, h, false, hovered);
        String name = path.endsWith(".json") ? path.substring(0, path.length() - 5) : path;
        g.drawString(minecraft.font, name, x + 4, y + 5, 0xFFFFFFFF, false);
        if (hovered) {
            g.drawString(minecraft.font, "✕", x + w - 12, y + 5, 0xFFFF5555, false);
        }
    }

    // ========== 自定义轻量列表组件（零多余背景渲染）==========

    @FunctionalInterface
    private interface ItemRenderer<T> {
        void render(GuiGraphics graphics, T item, int x, int y, int width, int height, boolean hovered);
    }

    /**
     * 完全自主控制的列表组件，不依赖 ObjectSelectionList。
     * 只做三件事：裁剪区域 + 绘制可见条目 + 滚动条。没有多余背景。
     */
    private class SimpleListWidget<T> implements GuiEventListener, Renderable, NarratableEntry {
        final int x, y, width, height, itemHeight;
        final ItemRenderer<T> renderer;
        final net.minecraft.client.gui.Font font;
        final List<T> items = new ArrayList<>();
        T selected = null;
        double scrollAmount = 0;

        SimpleListWidget(int x, int y, int width, int height, int itemHeight, ItemRenderer<T> renderer, net.minecraft.client.gui.Font font) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.itemHeight = itemHeight;
            this.renderer = renderer;
            this.font = font;
        }

        void add(T item) { items.add(item); }
        void clear() { items.clear(); selected = null; scrollAmount = 0; }
        T getSelected() { return selected; }
        void setSelected(T item) { this.selected = item; }
        int getX() { return x; }
        int getRowWidth() { return width - 4; }

        boolean isInBounds(double mx, double my) {
            return mx >= x && mx <= x + width && my >= y && my <= y + height;
        }

        int getMaxScroll() {
            int totalH = items.size() * itemHeight;
            return Math.max(0, totalH - height);
        }

        boolean mouseScrolled(double delta) {
            if (items.isEmpty()) return false;
            scrollAmount = Mth.clamp(scrollAmount - delta * itemHeight * 0.5, 0, getMaxScroll());
            return true;
        }

        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            if (items.isEmpty()) return;

            // 裁剪到列表区域内
            GuiCompat.enableScissor(x, y, width, height);

            int visibleCount = Math.min(items.size(), height / itemHeight + 2); // 多算2个防止边缘闪烁
            int maxOffset = getMaxScroll();
            scrollAmount = Mth.clamp(scrollAmount, 0, maxOffset);

            int startIdx = (int)(scrollAmount / itemHeight);
            int pixelOffset = (int)(scrollAmount % itemHeight);
            int drawY = y - pixelOffset;

            for (int i = startIdx; i < items.size(); i++) {
                T item = items.get(i);
                int entryTop = drawY + (i - startIdx) * itemHeight;
                if (entryTop + itemHeight < y) continue;   // 上方不可见
                if (entryTop > y + height) break;           // 下方不可见

                boolean hovered = isInBounds(mouseX, mouseY)
                        && mouseX >= x && mouseX <= x + width
                        && mouseY >= entryTop && mouseY <= entryTop + itemHeight;
                renderer.render(graphics, item, x + 2, entryTop, width - 4, itemHeight, hovered);
            }

            com.mojang.blaze3d.systems.RenderSystem.disableScissor();

            // 滚动条
            if (maxOffset > 0) {
                int thumbHeight = Math.max(10, height * height / (items.size() * itemHeight));
                int thumbMax = height - thumbHeight;
                int thumbY = y + (thumbMax > 0 ? (int)((double)scrollAmount / maxOffset * thumbMax) : 0);
                graphics.fill(x + width - 3, thumbY, x + width - 1, thumbY + thumbHeight, 0xFFAAAAAA);
            }
        }

        @Override
        public boolean mouseClicked(double mx, double my, int button) {
            if (!isInBounds(mx, my) || button != 0) return false;
            if (items.isEmpty()) return false;

            int relY = (int)(my - y + scrollAmount);
            int idx = relY / itemHeight;
            if (idx >= 0 && idx < items.size()) {
                T clicked = items.get(idx);
                if (clicked instanceof LibraryItem libItem) {
                    allBlueprintsList.setSelected(libItem);
                } else if (clicked instanceof String s) {
                    // Bound item: check X button click
                    int rowW = getRowWidth();
                    if (mx > x + rowW - 15) {
                        NetworkService.getInstance().sendWorkbenchAction(WorkbenchActionPayload.Action.UNBIND, s);
                        return true;
                    }
                }
                selected = clicked;
                return true;
            }
            return false;
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) { return isInBounds(mouseX, mouseY); }
        @Override
        public boolean isFocused() { return false; }
        @Override
        public void setFocused(boolean focused) {}
        @Override
        public NarrationPriority narrationPriority() { return NarrationPriority.NONE; }
        @Override
        public void updateNarration(net.minecraft.client.gui.narration.NarrationElementOutput output) {}
    }

    // ========== 数据类 ==========

    private record LibraryItem(String path) {
        String displayName() { return path.endsWith(".json") ? path.substring(0, path.length() - 5) : path; }
    }
}
