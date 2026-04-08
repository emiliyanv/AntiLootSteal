[README.md](https://github.com/user-attachments/files/26578268/README.md)
<div align="center">

# 🔴 Anti**Loot**Steal 🔴

**Plugin Documentation**

![Version](https://img.shields.io/badge/version-1.0.0-red?style=for-the-badge)
![Minecraft](https://img.shields.io/badge/MC-1.8--1.21+-orange?style=for-the-badge)
![Dependencies](https://img.shields.io/badge/dependencies-none-brightgreen?style=for-the-badge)

⚡ **Lightweight** · ⚔ **1.8 - 1.21+** · ⚙ **No Dependencies**

</div>

---

## ⭐ Features

| | Feature | Description |
|---|---|---|
| 🔒 | **Loot Protection** | When a player kills a mob or player, dropped items are locked to the killer for a configurable time. Nobody else can steal them. |
| ⏱ | **Configurable Timer** | Set the protection duration to any value in seconds via config.yml. Default is 15 seconds. |
| 🌈 | **Color Hologram** | A floating, color-changing countdown appears above each protected item. Green → Yellow → Red as time runs out. |
| 🛡 | **Entity Filtering** | Choose which kill types trigger protection: Player kills (PvP), Zombie kills, and Animal kills. Each toggleable in config. |
| 🌍 | **Per-World Control** | Enable or disable protection in specific worlds. Whitelist or blacklist worlds as needed. |
| 🔑 | **Permission Based** | Full permission support. Control who gets protection, who can bypass it, and who can use admin commands. |
| 💬 | **Custom Messages** | All player-facing messages are fully customizable with Minecraft color codes (`&a`, `&c`, `&e`, etc.). |
| ⚡ | **Lightweight** | No external dependencies. Uses O(1) lookups with ConcurrentHashMap. Minimal performance impact even with 100+ items. |

---

## 📦 Installation

1. Download **AntiLootSteal-1.0.0.jar**
2. Place it in your server's `/plugins/` folder
3. Restart or reload your server
4. A default `config.yml` will be generated in `/plugins/AntiLootSteal/`
5. Edit the config to your liking, then run `/antilootsteal reload`

---

## ⌨ Commands

| Command | Description |
|---|---|
| `/antilootsteal` | Shows plugin version and help information |
| `/antilootsteal reload` | Reloads config.yml without restarting the server. Requires `antilootsteal.admin` |
| `/als reload` | Short alias — same as `/antilootsteal reload` |

---

## 🔑 Permissions

| Permission | Description | Default |
|---|---|---|
| `antilootsteal.protect` | Loot from your kills is protected by the timer. Without this, kills drop items normally. | ✅ Everyone |
| `antilootsteal.bypass` | Bypass all loot protection. Pick up any protected loot instantly, ignoring the timer. | 🔒 OP Only |
| `antilootsteal.admin` | Access to `/antilootsteal reload` and all admin commands. | 🔒 OP Only |

### LuckPerms Examples

```bash
# Give all players loot protection (default)
/lp group default permission set antilootsteal.protect true

# VIP players can bypass protection
/lp group vip permission set antilootsteal.bypass true

# Only admins can reload
/lp group admin permission set antilootsteal.admin true
```

---

## ⚙ Configuration Guide

### Protection Duration

```yaml
# Protection timer in seconds
protection-duration: 15
```

How long items are protected after a kill. Only the killer can pick them up during this time.

| Value | Use Case |
|---|---|
| `5` | Very short, fast-paced servers |
| `15` | Default, balanced for most servers |
| `30` | Long protection, strict anti-steal |
| `60` | 1 minute, maximum protection |

### World Filtering

```yaml
# Worlds where protection is enabled (empty = all worlds)
enabled-worlds: []

# Worlds where protection is explicitly disabled
disabled-worlds:
  - world_creative
```

- **enabled-worlds:** If empty `[]`, protection works in ALL worlds. If you list world names, protection ONLY works in those worlds.
- **disabled-worlds:** Worlds listed here will never have loot protection. This takes priority over enabled-worlds.

> **Example A** — Everywhere except creative:
> `enabled-worlds: []` / `disabled-worlds: [world_creative]`
>
> **Example B** — Only in survival and nether:
> `enabled-worlds: [world, world_nether]` / `disabled-worlds: []`

### Protected Entity Types

```yaml
protected-entities:
  players: true
  zombies: true
  animals: true
```

Toggle which entity types trigger loot protection when killed by a player:

| Key | Entities |
|---|---|
| **players** | PvP kills (Player drops) |
| **zombies** | Zombie, Husk, Drowned, Zombie Villager |
| **animals** | Cow, Pig, Sheep, Chicken, Horse, etc. |

> **PvP server:** `players: true`, `zombies: false`, `animals: false`
>
> **PvE server:** `players: false`, `zombies: true`, `animals: true`

### Messages

```yaml
messages:
  loot-protected: "&c&lAntiLootSteal &8> &7This loot belongs to &e{player}&7! Wait &e{time}s&7."
  loot-expired: "&a&lAntiLootSteal &8> &7Loot protection has expired."
  reload-success: "&a&lAntiLootSteal &8> &7Config reloaded successfully."
  no-permission: "&c&lAntiLootSteal &8> &7You don't have permission."
```

All messages support **& color codes** and these placeholders:

| Placeholder | Description |
|---|---|
| `{player}` | Name of the killer (loot owner) |
| `{time}` | Seconds remaining on the protection timer |

### Color Code Reference

```
&0 Black        &1 Dark Blue    &2 Dark Green   &3 Dark Aqua
&4 Dark Red     &5 Purple       &6 Gold         &7 Gray
&8 Dark Gray    &9 Blue         &a Green        &b Aqua
&c Red          &d Pink         &e Yellow       &f White

&l Bold   &o Italic   &n Underline   &m Strikethrough   &k Obfuscated   &r Reset
```

---

## 🌈 Hologram System

### Hologram Config

```yaml
hologram:
  enabled: true
  y-offset: 1.2
  color-high: "&a"       # Green
  color-medium: "&e"     # Yellow
  color-low: "&c"        # Red
  medium-threshold: 10
  low-threshold: 5
  format: "{color}&l{time}s &7| &f{player}'s loot"
  update-interval: 20
```

### Live Preview

This is how the hologram looks in-game as the timer counts down:

```
🟢 15s | Steve's loot   →   🟡 8s | Steve's loot   →   🔴 3s | Steve's loot
   HIGH (15s-11s)              MEDIUM (10s-6s)             LOW (5s-0s)
```

| Setting | Description |
|---|---|
| **enabled** | Toggle holograms on/off. Protection still works when disabled. |
| **y-offset** | Height above the item (in blocks). `1.2` is default. |
| **color-high / medium / low** | Color codes for each phase of the countdown. |
| **medium-threshold** | Seconds remaining when color changes from green → yellow. |
| **low-threshold** | Seconds remaining when color changes from yellow → red. |
| **update-interval** | Refresh rate in ticks. `20` = 1 second (recommended). `10` = 0.5s (smoother). |

**Format placeholders:**

| Placeholder | Description |
|---|---|
| `{color}` | Auto-replaced with the current phase color |
| `{time}` | Seconds remaining |
| `{player}` | Killer's name |

**Format examples:**

```
{color}&l{time}s &7| &f{player}'s loot  →  12s | Steve's loot
&f[&c{time}s&f] {player}              →  [12s] Steve
{color}&l>> &f{player} &7- {color}{time}s →  >> Steve - 8s
```

---

## 📖 Usage Examples

### ⚔ Scenario 1: Player kills a Zombie

1. **Steve** kills a Zombie. It drops **Rotten Flesh** and an **Iron Sword**.
2. Both items are now **protected for 15 seconds**.
3. A **green hologram** appears: **`15s | Steve's loot`**
4. The timer counts down. At 10s it turns **yellow**, at 5s it turns **red**.
5. **Steve** can pick up items at any time during the timer.
6. If **Alex** tries to pick them up, she is **denied** and sees a message.
7. After 15s, the hologram disappears and items become **free for everyone**.

### ⚔ Scenario 2: PvP Kill

1. **Alex** kills **Steve** in PvP combat.
2. Steve's entire dropped inventory (armor, weapons, food) is **locked to Alex**.
3. No other player can swoop in and steal the loot. Alex has full priority.
4. This prevents **"loot vultures"** from stealing kills in PvP.

### 🛡 Scenario 3: Admin Bypass

1. **Steve** kills a Cow. The Raw Beef is protected.
2. **Admin** has the `antilootsteal.bypass` permission.
3. Admin walks over the items and picks them up **instantly**, ignoring the timer.

### ⚙ Scenario 4: Selective Protection

1. Config: `animals: false`, `players: true`
2. Steve kills a Sheep → Drops are **free for anyone** (no protection).
3. Steve kills a Player → Drops **are protected** with full timer + hologram.

---

## ❓ FAQ

<details>
<summary><b>Does it work with Paper / Purpur?</b></summary>
Yes. Paper, Purpur, and all Spigot forks are fully compatible.
</details>

<details>
<summary><b>Does it work with Minecraft 1.8?</b></summary>
Yes. Compiled against Spigot 1.8.8 API with Java 8 target. Works on all versions from 1.8.8 to the latest.
</details>

<details>
<summary><b>Will items despawn while protected?</b></summary>
Items follow vanilla despawn rules (5 minutes). Protection does not affect despawn timers.
</details>

<details>
<summary><b>What if the killer disconnects?</b></summary>
Loot stays protected until the timer expires, then becomes free. This is intended behavior.
</details>

<details>
<summary><b>Can protected items merge with others?</b></summary>
No. The plugin prevents merging of protected items to ensure accurate tracking. After expiry, they merge normally.
</details>

<details>
<summary><b>Does it protect experience orbs?</b></summary>
No. Only item drops are protected. Experience drops normally for whoever picks them up.
</details>

<details>
<summary><b>What happens on server restart?</b></summary>
All active protections are cleared on shutdown (holograms are properly removed). New kills after restart create fresh protections. Since timers are short-lived, this is by design.
</details>

<details>
<summary><b>Does the hologram lag the server?</b></summary>
No. Each hologram is one invisible armor stand updated once per second. Even 100+ items have negligible impact.
</details>

<details>
<summary><b>Does it work with LuckPerms / PermissionsEx?</b></summary>
Yes. All standard Bukkit-compatible permission plugins work out of the box.
</details>

<details>
<summary><b>Can I disable holograms but keep protection?</b></summary>
Yes. Set <code>hologram: enabled: false</code> in config.yml. Items will still be protected; players just won't see the floating text.
</details>

---

<div align="center">

**AntiLootSteal v1.0.0** · Spigot 1.8.8 - 1.21+

</div>
