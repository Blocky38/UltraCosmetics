package be.isach.ultracosmetics.cosmetics.pets;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.PetType;
import be.isach.ultracosmetics.player.UltraPlayer;
import com.cryptomorin.xseries.XMaterial;

/**
 * Represents an instance of a fox pet summoned by a player.
 *
 * @author Chris6ix
 * @since 14-01-2022
 */
public class PetFox extends Pet {
    public PetFox(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(owner, ultraCosmetics, PetType.getByName("fox"), XMaterial.SWEET_BERRIES);
    }
}
