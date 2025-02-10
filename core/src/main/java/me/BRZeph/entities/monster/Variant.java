package me.BRZeph.entities.monster;

import me.BRZeph.core.Assets.AdvancedAssetsManager;

import static me.BRZeph.utils.Constants.Constants.Paths.MonstersValues.VariantValues.*;

public enum Variant {
    NORMAL(NORMAL_HP_MOD,NORMAL_SPD_MOD, NORMAL_NEXUS_DMG_MOD),
    ARMORED(ARMORED_HP_MOD,ARMORED_SPD_MOD, ARMORED_NEXUS_DMG_MOD),
    CORRUPTED(CORRUPTED_HP_MOD, CORRUPTED_SPD_MOD, CORRUPTED_NEXUS_DMG_MOD),
    ELITE(ELITE_HP_MOD, ELITE_SPD_MOD, ELITE_NEXUS_DMG_MOD);

    private float HPMod, SPDMod, nexusDMGMod;

    /*
    Corrupted monsters scale stats with waves.
    All corrupted monsters have the same scaling.
    A monster can be normal, armored + corrupted or elite + corrupted but not armored + elite.
     */


    Variant(float HPMod, float SPDMod, float nexusDMGMod){
        this.HPMod = HPMod;
        this.SPDMod = SPDMod;
        this.nexusDMGMod = nexusDMGMod;
    }

    public static void applyVariant(Monster monster, Variant variant, int wave){
        int corruption = 0;
        if(variant == CORRUPTED){
            corruption = 1;
        }

        monster.setMaxHealth(
            monster.getMaxHealth() * variant.getHPMod() + // + 0.0[wave]% hp per wave
            monster.getMaxHealth() * CORRUPTION_SCALING_HP * wave * corruption
        );
        monster.setSpeed(
            monster.getSpeed() * variant.getSPDMod() + // + 0.0[wave]% speed per wave
            monster.getSpeed() * CORRUPTION_SCALING_SPEED * wave * corruption
        );
        monster.setNexusDmg(
            monster.getNexusDmg() * variant.nexusDMGMod + // + 0.0[wave]% nexus damage per wave
            monster.getNexusDmg() * CORRUPTION_SCALING_NEXUS_DMG * wave * corruption
        );




        String variantWalkAnimationName = monster.getType().getWalkAnimationName();
        // After implementing the animations,change this ^^.
        // All of these textures are for the normal variant, other variants follow the pattern:
        // [normal name -4*]_[variant].png
        // Example:
        // Zombie normal texture -> Textures\Enemies\Zombie.png
        // Zombie elite texture ->  Textures\Enemies\Zombie_elite.png
        monster.setWalkAnimation(AdvancedAssetsManager.getAnimation(variantWalkAnimationName));
    }

    public float getHPMod() {
        return HPMod;
    }

    public float getSPDMod() {
        return SPDMod;
    }
}
