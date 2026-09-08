# Mebahel's Skeleton Revival v1.1.0

This update gives living skulls more personality and adds several options requested by players. Skulls now preserve important details from the mob they came from, while server owners have more control over revival balance, drops and spawner behavior.

## New features

- Living skulls now keep the original mob's custom name.
- Active status effects are transferred to the skull. Their remaining duration continues to count down and any effects still active are passed back to the revived mob.
- Head-slot equipment is now visible on the living skull and is returned to the revived mob with its enchantments, durability and other item data intact.
- A living skull can now drop the matching Skeleton Skull or Wither Skeleton Skull item, with a 3.3% chance.
- Skull health can now scale with the original mob's maximum health.
- Revived mobs can optionally create another living skull, allowing unlimited revival cycles.
- Fall damage for living skulls can now be enabled or disabled.
- Mobs spawned by spawners can now be included in the revival system.
- Experience from living skulls and experience or loot from revived mobs can now be controlled separately.

## New configuration options

The following settings are added automatically to `config/mebahels-skeleton-revival/mebahels-skeleton-revival_config.json`:

```json
{
  "skeletonHeadHealthPercentage": 40,
  "reanimatedEntitiesCanSpawnHeads": false,
  "skeletonHeadTakesFallDamage": false,
  "skeletonHeadShouldDropExperience": true,
  "respawnedEntityShouldDropExperience": true,
  "respawnedEntityShouldDropItem": true,
  "canSpawnFromSpawner": false
}
```

- `skeletonHeadHealthPercentage` sets the skull's maximum health to a percentage of the original mob's maximum health. Accepted values range from `1` to `100`.
- `reanimatedEntitiesCanSpawnHeads` allows revived mobs to create new living skulls when enabled.
- `skeletonHeadTakesFallDamage` controls whether living skulls take fall damage.
- `skeletonHeadShouldDropExperience` controls experience drops from living skulls.
- `respawnedEntityShouldDropExperience` controls experience drops from revived mobs.
- `respawnedEntityShouldDropItem` controls normal loot drops from revived mobs.
- `canSpawnFromSpawner` allows mobs created by spawners to leave living skulls behind.

All new settings use safe defaults that preserve the standard single-revival behavior.
