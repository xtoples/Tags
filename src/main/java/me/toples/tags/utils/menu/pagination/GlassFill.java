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
public class GlassFill extends Button {

    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.STAINED_GLASS_PANE);

        item.durability((byte) 7);

        item.name(Color.translate(" "));

        return item.build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
    }


}
