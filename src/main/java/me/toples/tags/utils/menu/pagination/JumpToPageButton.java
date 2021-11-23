package me.toples.tags.utils.menu.pagination;

import me.toples.tags.utils.menu.Button;
import lombok.AllArgsConstructor;
import me.toples.tags.utils.Color;
import me.toples.tags.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class JumpToPageButton extends Button {

    private int page;
    private PaginatedMenu menu;
    private boolean current;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder item = new ItemBuilder(this.current ? Material.EMPTY_MAP : Material.PAPER);

        item.amount(this.page);
        item.name(Color.translate("&7Page " + this.page));
        if (this.current) item.lore("&7Current Page");

        return item.build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }

}

