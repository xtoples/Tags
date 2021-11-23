package me.toples.tags.tags.menu;

import com.cryptomorin.xseries.XMaterial;
import me.toples.tags.profiles.Profile;
import me.toples.tags.tags.impl.TagHandler;
import me.toples.tags.utils.menu.Button;
import me.toples.tags.utils.menu.pagination.PaginatedMenu;
import me.toples.tags.tags.Tag;
import me.toples.tags.utils.Color;
import me.toples.tags.utils.ItemBuilder;
import me.toples.tags.utils.menu.CC;
import me.toples.tags.utils.menu.buttons.Glass;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TagsMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return Color.translate("&7Tags");
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        int i = 0;
        for (Tag tag : TagHandler.getTags()) {
            if (i < 27) {
                toReturn.put(i, new Button() {
                    @Override
                    public ItemStack getButtonItem(Player player) {
                        Profile profile = Profile.getByUUID(player.getUniqueId());

                        if (profile.getTag() == null) {
                            if (!player.hasPermission(tag.getPermission())) {
                                return new ItemBuilder(XMaterial.RED_DYE.parseItem()).name(CC.PRIMARY + tag.getName()).lore(Arrays.asList(
                                        Color.translate("&7&m------------------------"),
                                        CC.PRIMARY + "Tag: " + CC.SECONDARY + Color.translate(tag.getPrefix()),
                                        Color.translate("&7&m------------------------"),
                                        "",
                                        CC.RED + "You do not have access to this tags!"
                                )).build();
                            }

                            if (player.hasPermission(tag.getPermission())) {
                                return new ItemBuilder(XMaterial.GRAY_DYE.parseItem()).name(CC.PRIMARY + tag.getName()).lore(Arrays.asList(
                                        Color.translate("&7&m------------------------"),
                                        CC.PRIMARY + "Tag: " + CC.SECONDARY + Color.translate(tag.getPrefix()),
                                        Color.translate("&7&m------------------------"),
                                        "",
                                        CC.GREEN + "Click to set this as your tags."
                                )).build();
                            }
                            return null;
                        }
                        if (profile.getTag().equals(tag) && player.hasPermission(tag.getPermission())) {
                            return new ItemBuilder(XMaterial.LIME_DYE.parseItem()).name(CC.PRIMARY + tag.getName()).lore(Arrays.asList(
                                    Color.translate("&7&m------------------------"),
                                    CC.PRIMARY + "Tag: " + CC.SECONDARY + Color.translate(tag.getPrefix()),
                                    Color.translate("&7&m------------------------"),
                                    "",
                                    CC.RED + "You already equipped this tags!"
                            )).build();
                        }

                        if (!player.hasPermission(tag.getPermission())) {
                            return new ItemBuilder(XMaterial.RED_DYE.parseItem()).name(CC.PRIMARY + tag.getName()).lore(Arrays.asList(
                                    Color.translate("&7&m-------------"),
                                    CC.PRIMARY + "Tag: " + CC.SECONDARY + Color.translate(tag.getPrefix()),
                                    Color.translate("&7&m-------------"),
                                    "",
                                    CC.RED + "You do not have access to this tags!"
                            )).build();
                        }

                        if (player.hasPermission(tag.getPermission()) && !profile.getTag().equals(tag)) {
                            return new ItemBuilder(XMaterial.GRAY_DYE.parseItem()).name(CC.PRIMARY + tag.getName()).lore(Arrays.asList(
                                    Color.translate("&7&m------------------------"),
                                    CC.PRIMARY + "Tag: " + CC.SECONDARY + Color.translate(tag.getPrefix()),
                                    Color.translate("&7&m------------------------"),
                                    "",
                                    CC.GREEN + "Click to set this as your tags."
                            )).build();
                        }

                        return null;
                    }

                    @Override
                    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                        if (player.hasPermission(tag.getPermission())) {
                            Profile prof = Profile.getByUUID(player.getUniqueId());
                            if (prof.getTag() == null) {
                                prof.setTag(tag);
                                player.sendMessage(CC.PRIMARY + "You have successfully set your tags to " + CC.SECONDARY + tag.getName());
                                return;
                            }
                            if (prof.getTag().equals(tag)) {
                                player.sendMessage(CC.RED + "You already equipped this tags!");
                                return;
                            }

                            if (prof.getTag() != tag) {
                                prof.setTag(tag);
                                prof.save();
                                player.sendMessage(CC.PRIMARY + "You have successfully set your tags to " + CC.SECONDARY + tag.getName());
                                return;
                            }
                        }else {
                            player.sendMessage(CC.RED + "You do not have permission to use this tags.");
                        }

                    }
                });
                i++;
            }

        }

        return toReturn;
    }

    @Override
    public int getSize() {
        return 9*4;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        toReturn.put(27, new Glass());
        toReturn.put(28, new Glass());
        toReturn.put(29, new Glass());
        toReturn.put(30, new Glass());
        toReturn.put(31, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(XMaterial.HOPPER.parseItem()).name(CC.YELLOW + "Click to reset your tags!").build();
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                Profile profile = Profile.getByUUID(player.getUniqueId());
                if (profile.getTag() == null) {
                    player.sendMessage(CC.RED + "You don't have any tags enabled!");
                    return;
                }

                profile.setTag(null);
                profile.save();
                player.sendMessage(CC.PRIMARY + "Successfully cleared your tags!");
            }


        });
        toReturn.put(32, new Glass());
        toReturn.put(33, new Glass());
        toReturn.put(34, new Glass());
        toReturn.put(35, new Glass());



        return toReturn;
    }
}
