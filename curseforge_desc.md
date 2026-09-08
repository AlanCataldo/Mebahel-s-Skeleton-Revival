# Mebahel's Skeleton Revival

Killing a skeleton is no longer always the end of the fight.

When a Skeleton or Wither Skeleton dies, it has a configurable chance to leave behind a living skull. The skull immediately tries to escape from nearby players and creatures. If nobody destroys it in time, it stops, plays its revival animation, and brings the original mob back.

This adds a small but noticeable change to combat: you can chase the skull to finish the job, or ignore it and risk fighting the skeleton again a few seconds later.

***

## Dependencies

### Forge / NeoForge

* [Sinytra Connector](https://www.curseforge.com/minecraft/mc-mods/sinytra-connector)
* [Forgified Fabric API](https://www.curseforge.com/minecraft/mc-mods/forgified-fabric-api)
* [GeckoLib](https://www.curseforge.com/minecraft/mc-mods/geckolib)

### Fabric

* [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
* [GeckoLib](https://www.curseforge.com/minecraft/mc-mods/geckolib)

***

## How it works

- Skeletons and Wither Skeletons have separate revival chances.
- The living skull keeps the correct Skeleton or Wither Skeleton appearance.
- The skull carries the original mob's custom name, active status effects and head-slot item, including its enchantments. These are passed back to the revived mob.
- Revived mobs cannot create another living skull by default. This protection can be disabled in the config if you want an unlimited revival cycle.
- Mobs created by spawners are ignored by default, but this can be changed in the config.
- The skull can be killed before the timer ends. It has its own health, loot and animations.
- Modded living entities can use the same system through the entity-list config.

***

## Configuration

Both config files are created after the game is launched once. They can be found in:

`config/mebahels-skeleton-revival/`

Restart the game or server after editing them.

### Main settings

File: `mebahels-skeleton-revival_config.json`

```json
{
  "skeletonHeadSpawnRate": 12,
  "witherSkeletonHeadSpawnRate": 12,
  "timeBeforeRevival": 140,
  "skeletonHeadHealthPercentage": 40,
  "reanimatedEntitiesCanSpawnHeads": false,
  "skeletonHeadTakesFallDamage": false,
  "skeletonHeadShouldDropExperience": true,
  "respawnedEntityShouldDropExperience": true,
  "respawnedEntityShouldDropItem": true,
  "canSpawnFromSpawner": false
}
```

- `skeletonHeadSpawnRate`: chance, from `0` to `100`, for an entity in the Skeleton list to create a living skull when it dies.
- `witherSkeletonHeadSpawnRate`: same setting for entities in the Wither Skeleton list.
- `timeBeforeRevival`: time before the skull revives its original mob, in ticks. Minecraft runs at 20 ticks per second, so the default value of `140` is 7 seconds. Values below `30` are reset to the default.
- `skeletonHeadHealthPercentage`: living skull health as a percentage of the original mob's maximum health, from `1` to `100`. The default value of `40` gives a skull 40% of the original mob's maximum health.
- `reanimatedEntitiesCanSpawnHeads`: when set to `true`, revived mobs can create another living skull when they die, allowing the revival cycle to continue indefinitely.
- `skeletonHeadTakesFallDamage`: controls whether living skulls take fall damage.
- `skeletonHeadShouldDropExperience`: controls whether a living skull can drop experience.
- `respawnedEntityShouldDropExperience`: controls whether the revived mob can drop experience when killed again.
- `respawnedEntityShouldDropItem`: controls whether the revived mob can drop its normal loot when killed again.
- `canSpawnFromSpawner`: when set to `true`, mobs created by spawners can also leave living skulls behind.

Spawn rates outside the `0` to `100` range are replaced with their default value of `12`.

### Adding other entities

File: `entity-type-that-should-spawn-head_config.json`

```json
{
  "skeletonHeadEntities": [
    "minecraft:skeleton",
    "modid:custom_archer"
  ],
  "witherSkeletonHeadEntities": [
    "minecraft:wither_skeleton",
    "modid:dark_knight"
  ]
}
```

Add an entity ID to one of these lists to include it in the revival system. Entries in `skeletonHeadEntities` create a regular skeleton skull, while entries in `witherSkeletonHeadEntities` create a wither skeleton skull. The revived creature is the same entity type that originally died, including entities added by other mods.

Entity IDs use the `namespace:entity_name` format. For example, the vanilla Skeleton is `minecraft:skeleton`. You will need the exact registered ID when adding an entity from another mod.

***

## Feedback and support

The mod is still being developed. Bug reports, balance feedback and compatibility suggestions are welcome on the [Discord server](https://discord.com/invite/y8uC2NepkB).
