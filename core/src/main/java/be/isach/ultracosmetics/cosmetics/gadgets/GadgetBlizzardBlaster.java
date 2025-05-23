package be.isach.ultracosmetics.cosmetics.gadgets;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.cosmetics.PlayerAffectingCosmetic;
import be.isach.ultracosmetics.cosmetics.Updatable;
import be.isach.ultracosmetics.cosmetics.type.GadgetType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.BlockUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Represents an instance of a blizzard blaster gadget summoned by a player.
 *
 * @author iSach
 * @since 08-08-2015
 */
public class GadgetBlizzardBlaster extends Gadget implements PlayerAffectingCosmetic, Updatable {

    private boolean active;
    private Location location;
    private Vector vector;

    public GadgetBlizzardBlaster(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(owner, GadgetType.valueOf("blizzardblaster"), ultraCosmetics);
    }

    @Override
    void onRightClick() {
        this.vector = getPlayer().getLocation().getDirection().normalize().multiply(0.3);
        this.vector.setY(0);

        this.location = getPlayer().getLocation().subtract(0, 1, 0).add(vector);
        this.active = true;

        Bukkit.getScheduler().runTaskLater(getUltraCosmetics(), this::clean, 40);

    }

    @Override
    public void onUpdate() {
        if (active) {
            if (location.getBlock().getType().isSolid()) {
                location.add(0, 1, 0);
            }

            if (BlockUtils.isAir(location.clone().subtract(0, 1, 0).getBlock().getType())) {
                if (!location.clone().getBlock().getType().toString().contains("SLAB"))
                    location.add(0, -1, 0);
            }

            for (int i = 0; i < 3; i++) {
                UltraCosmeticsData.get().getVersionManager().getEntityUtil()
                        .sendBlizzard(getPlayer(), location, this::canAffect, vector);
            }

            location.add(vector);
        } else {
            location = null;
            vector = null;
        }
    }

    @Override
    public void onClear() {
        if (getOwner() == null || getPlayer() == null) {
            return;
        }
        UltraCosmeticsData.get().getVersionManager().getEntityUtil().clearBlizzard(getPlayer());
    }

    private void clean() {
        active = false;
    }
}
