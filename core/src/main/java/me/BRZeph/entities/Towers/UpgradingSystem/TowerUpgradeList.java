package me.BRZeph.entities.Towers.UpgradingSystem;

import com.badlogic.gdx.Gdx;
import me.BRZeph.TowerDefenseGame;
import me.BRZeph.entities.Projectile;
import me.BRZeph.entities.monster.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static me.BRZeph.utils.Constants.Constants.PlaceHolderValues.PLACE_HOLDER_TEXTURE_PATH;
import static me.BRZeph.utils.Constants.TowerUpgradeConstants.ArcherTowerUpgrades.*;

public class TowerUpgradeList {

    // Neutral towers:
    /*
                    * * * * ARCHER TOWER * * * *
    PATH 1:
        -> Sharpened Arrows -> Slightly increases damage.
        -> Longbow -> Extends range significantly.
        -> Bouncing shot -> Hits a second target.
        -> Rain of Arrows -> Periodically rains arrows over a large area, damaging all enemies (this is kinda bad ngl).
    PATH 2:
        -> Quick Draw -> Increases attack speed.
        -> Precision aim -> Adds a chance to critically hit for increased % damage.
        -> Rapid Volley -> Fires two arrows at the same time (targeting different enemies).
        -> Sniper mode -> The greater the distance is, the greater the damage will be.
    PATH 3:
        -> Marksmanship Training -> Hitting an enemy marks it making it take increased damage from all sources.
        -> Chain Signal -> When the marked target dies, it bounces to another enemy, limit of 5 marks at the same time.
        -> Coordinated Assault -> Towers attacking the marked enemy have less attack cooldown.
        -> Tactical Barrage -> Periodically fires a volley of arrows at the market targets.


                    * * * * CANNON TOWER * * * *
    PATH 1:
        -> Reinforced Shells -> Increases single target damage.
        -> Armor-Piercing Rounds -> Deals extra damage to elite and armored enemies.
        -> High-Impact Cannons -> Knocks back enemies on hit.
        -> Obliterator -> Huge hit that deals massive damage single target and a bit more AOE.
    PATH 2:
        -> Explosive Payload -> Increases AOE damage.
        -> Cluster Rounds -> Upon hitting spawns more bombs in an area that explodes.
        -> Shockwave Impact -> Stuns hit enemies (does not apply to cluster rounds, just the main hit).
        -> Devastator -> Massively increases all AOE damage.
    PATH 3:
        -> Extended Barrel -> Slightly increases range.
        -> Quick Reload -> Increases attack speed.
        -> Double barrel -> Fires two hits at the same time (kinda bad ngl).
        -> Gatling Cannon -> Converts into a rapid-firing cannon with smaller shells.


                    * * * * LIGHTNING TOWER * * * *
    PATH 1:
        -> Forked Lightning -> Increases bounce by 2.
        -> Arcing Surge -> Increases bounce by 3 and stuns briefly.
        -> Thunder Cascade -> Increases bounce by 3 with greater stun to first target.
        -> Maelstrom Arc -> Infinite bounce with the same stun properties that creates a shocking aura on the first target that damages all nearby enemies.
    PATH 2:
        -> Amplified Shock -> Increases bounce damage and main hit by 20%.
        -> Focused Discharge -> Bounce now has 20% to deal X% extra damage.
        -> Ionized Strike -> Enemies damaged become "ionized" taking increased damage from subsequent lightning attacks for X seconds.
        -> Stormcaller’s Wrath -> First target takes heavily increased damage with a X% chance of repeating the attack for free.
    PATH 3:
        -> Static Field -> Emits a pulse that shocks nearby enemies slowing them by X%.
        -> Charged Terrain -> Each pulse deals minor damage.
        -> Tempest Core -> Creates a localized storm that strikes random enemies within range.
        -> Eye of the Storm -> Massive storm envelops the area attacking all enemies within range.
     */

    // Light towers:
    /*

     */

    // Demonic towers:
    /*

     */

    public static class NeutralTowers{

        public static class ArcherUpgradeList{
            public static final List<TowerUpgrade> ARCHER_PATH_1 = getArcherPath1();
            public static final List<TowerUpgrade> ARCHER_PATH_2 = getArcherPath2();
            public static final List<TowerUpgrade> ARCHER_PATH_3 = getArcherPath3();
            private static List<TowerUpgrade> getArcherPath1() {
                return List.of(
                    new TowerUpgrade(AT_P1_T1_NAME, AT_P1_T1_DESCRIPTION, AT_P1_T1_GC, AT_P1_T1_EC, AT_P1_T1_MC,
                        1, 1, AT_P1_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            tower.setDamage(tower.getDamage() + AT_P1_T1_DMG_INCREASE);
                        }, null, null
                    ),

                    new TowerUpgrade(AT_P1_T2_NAME, AT_P1_T2_DESCRIPTION, AT_P1_T2_GC, AT_P1_T2_EC, AT_P1_T2_MC,
                        1, 2, AT_P1_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            tower.setAttackRange(tower.getRange() + AT_P1_T2_RNG_INCREASE);
                        }, null, null
                    ),

                    new TowerUpgrade(AT_P1_T3_NAME, AT_P1_T3_DESCRIPTION, AT_P1_T3_GC, AT_P1_T3_EC, AT_P1_T3_MC,
                        1, 3, AT_P1_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                        }, null,
                        (tower, waveManager) -> {
                            float delta = Gdx.graphics.getDeltaTime();
                            Iterator<Projectile> projectileIterator = tower.getActiveProjectiles().iterator();
                            List<Projectile> newProjectiles = new ArrayList<>();
                            while (projectileIterator.hasNext()) {
                                Projectile projectile = projectileIterator.next();
                                projectile.update(delta);
                                if (projectile.hasReachedTarget()) {
                                    if (!projectile.isOriginBounce()){
                                        Monster target = projectile.getTarget();
                                        Monster bounceMonster = waveManager.getCurrentWave().getMonsterList().stream()
                                            .filter(monster -> {
                                                int targetX = (int) target.getX();
                                                int targetY = (int) target.getY();
                                                return monster != null && monster != target &&
                                                    monster.getDistanceToPoint(targetX, targetY) <= AT_P1_T3_BOUNCE_MAX_RANGE;
                                            })
                                            .min((monster1, monster2) -> {
                                                double distance1 = monster1.getDistanceToPoint((int) target.getX(), (int) target.getY());
                                                double distance2 = monster2.getDistanceToPoint((int) target.getX(), (int) target.getY());
                                                return Double.compare(distance1, distance2);
                                            })
                                            .orElse(null);

                                        if (bounceMonster != null) {
                                            float bounceDamage = AT_P1_T3_BOUNCE_DAMAGE;
                                            Projectile newProjectile = new Projectile(
                                                target.getX(), target.getY(),
                                                AT_P1_T3_BOUNCE_SPEED,
                                                bounceDamage,
                                                bounceMonster,
                                                projectile.getTexture()
                                            );
                                            newProjectile.setOriginBounce(true);
                                            newProjectiles.add(newProjectile);
                                            bounceMonster.addIncomingDamage(bounceDamage);
                                        }
                                    }
                                    projectile.getTarget().takeDamage(projectile.getDamage());
                                    projectile.getTarget().subIncomingDamage(projectile.getDamage());
                                    projectileIterator.remove();
                                    tower.setDamageDealt(tower.getDamageDealt() + projectile.getDamage());
                                    if (projectile.getTarget().getCurrentHealth() <= 0){
                                        tower.addKills();
                                    }
                                }
                            }
                            for (Projectile projectile : newProjectiles){
                                tower.getActiveProjectiles().add(projectile);
                            }
                        }
                    ),

                    new TowerUpgrade(AT_P1_T4_NAME, AT_P1_T4_DESCRIPTION, AT_P1_T4_GC, AT_P1_T4_EC, AT_P1_T4_MC,
                        1, 4, AT_P1_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getArcherPath2() {
                return List.of(
                    new TowerUpgrade("Upgrade 5", "Increases Attack Speed",
                        120, 0, 50, 2, 1, AT_P2_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 6", "Adds Piercing Damage",
                        180, 0, 50, 2, 2, AT_P2_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 7", "Increases Range & Damage",
                        250, 0, 100, 2, 3, AT_P2_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 8", "Grants Bonus Health",
                        350, 0, 150, 2, 4, AT_P2_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getArcherPath3() {
                return List.of(
                    new TowerUpgrade("Upgrade 9", "Adds Fire Damage",
                        150, 100, 0, 3, 1, AT_P3_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 10", "Increases Damage vs Armored",
                        200, 150, 0, 3, 2, AT_P3_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 11", "Grants Knockback",
                        250, 200, 50, 3, 3, AT_P3_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),
                    new TowerUpgrade("Upgrade 12", "Improves Accuracy",
                        300, 250, 100, 3, 4, AT_P3_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
        }

        public static class CannonUpgradeList{
            public static final List<TowerUpgrade> CANNON_PATH_1 = getCannonPath1();
            public static final List<TowerUpgrade> CANNON_PATH_2 = getCannonPath2();
            public static final List<TowerUpgrade> CANNON_PATH_3 = getCannonPath3();
            private static List<TowerUpgrade> getCannonPath1() {
                return List.of(
                    new TowerUpgrade(AT_P1_T1_NAME, AT_P1_T1_DESCRIPTION, AT_P1_T1_GC, AT_P1_T1_EC, AT_P1_T1_MC,
                        1, 1, AT_P1_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                            tower.setDamage(tower.getDamage() + AT_P1_T1_DMG_INCREASE);
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T2_NAME, AT_P1_T2_DESCRIPTION, AT_P1_T2_GC, AT_P1_T2_EC, AT_P1_T2_MC,
                        1, 2, AT_P1_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T3_NAME, AT_P1_T3_DESCRIPTION, AT_P1_T3_GC, AT_P1_T3_EC, AT_P1_T3_MC,
                        1, 3, AT_P1_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T4_NAME, AT_P1_T4_DESCRIPTION, AT_P1_T4_GC, AT_P1_T4_EC, AT_P1_T4_MC,
                        1, 4, AT_P1_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getCannonPath2() {
                return List.of(
                    new TowerUpgrade("Upgrade 5", "Increases Attack Speed",
                        120, 0, 50, 2, 1, AT_P2_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 6", "Adds Piercing Damage",
                        180, 0, 50, 2, 2, AT_P2_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 7", "Increases Range & Damage",
                        250, 0, 100, 2, 3, AT_P2_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 8", "Grants Bonus Health",
                        350, 0, 150, 2, 4, AT_P2_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getCannonPath3() {
                return List.of(
                    new TowerUpgrade("Upgrade 9", "Adds Fire Damage",
                        150, 100, 0, 3, 1, AT_P3_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 10", "Increases Damage vs Armored",
                        200, 150, 0, 3, 2, AT_P3_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 11", "Grants Knockback",
                        250, 200, 50, 3, 3, AT_P3_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),
                    new TowerUpgrade("Upgrade 12", "Improves Accuracy",
                        300, 250, 100, 3, 4, AT_P3_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
        }

        public static class LightningUpgradeList{
            public static final List<TowerUpgrade> LIGHTNING_PATH_1 = getLightningPath1();
            public static final List<TowerUpgrade> LIGHTNING_PATH_2 = getLightningPath2();
            public static final List<TowerUpgrade> LIGHTNING_PATH_3 = getLightningPath3();
            private static List<TowerUpgrade> getLightningPath1() {
                return List.of(
                    new TowerUpgrade(AT_P1_T1_NAME, AT_P1_T1_DESCRIPTION, AT_P1_T1_GC, AT_P1_T1_EC, AT_P1_T1_MC,
                        1, 1, AT_P1_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                            tower.setDamage(tower.getDamage() + AT_P1_T1_DMG_INCREASE);
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T2_NAME, AT_P1_T2_DESCRIPTION, AT_P1_T2_GC, AT_P1_T2_EC, AT_P1_T2_MC,
                        1, 2, AT_P1_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T3_NAME, AT_P1_T3_DESCRIPTION, AT_P1_T3_GC, AT_P1_T3_EC, AT_P1_T3_MC,
                        1, 3, AT_P1_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade(AT_P1_T4_NAME, AT_P1_T4_DESCRIPTION, AT_P1_T4_GC, AT_P1_T4_EC, AT_P1_T4_MC,
                        1, 4, AT_P1_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        }, null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getLightningPath2() {
                return List.of(
                    new TowerUpgrade("Upgrade 5", "Increases Attack Speed",
                        120, 0, 50, 2, 1, AT_P2_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 6", "Adds Piercing Damage",
                        180, 0, 50, 2, 2, AT_P2_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 7", "Increases Range & Damage",
                        250, 0, 100, 2, 3, AT_P2_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 8", "Grants Bonus Health",
                        350, 0, 150, 2, 4, AT_P2_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
            private static List<TowerUpgrade> getLightningPath3() {
                return List.of(
                    new TowerUpgrade("Upgrade 9", "Adds Fire Damage",
                        150, 100, 0, 3, 1, AT_P3_T1_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 10", "Increases Damage vs Armored",
                        200, 150, 0, 3, 2, AT_P3_T2_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),

                    new TowerUpgrade("Upgrade 11", "Grants Knockback",
                        250, 200, 50, 3, 3, AT_P3_T3_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    ),
                    new TowerUpgrade("Upgrade 12", "Improves Accuracy",
                        300, 250, 100, 3, 4, AT_P3_T4_BUY_TEXTURE_NAME,
                        (tower) -> {
                            // Modify the tower's stats here. Example: increase attack speed.
                        },null, null
//                    (waveManager) -> {
//                        // Modify the tower's attack here. Example: instantaneous hit.
//                    },
//                    (delta, waveManager) -> {
//                        // Modify the tower's projectile here. Example: piercing projectiles that do not disappear upon hit.
//                    }
                    )
                );
            }
        }
    }

    public static class LightTowers{

    }

    public static class DemonicTowers{

    }
}
