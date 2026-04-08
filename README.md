[docs.html](https://github.com/user-attachments/files/26578194/docs.html)
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AntiLootSteal - Plugin Documentation</title>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&family=VT323&family=Silkscreen:wght@400;700&display=swap');

  :root {
    --mc-dark: #1d1d1d;
    --mc-stone: #7f7f7f;
    --mc-dirt: #866043;
    --mc-grass: #5b8731;
    --mc-green: #55ff55;
    --mc-red: #ff5555;
    --mc-gold: #ffaa00;
    --mc-yellow: #ffff55;
    --mc-aqua: #55ffff;
    --mc-purple: #aa00aa;
    --mc-pink: #ff55ff;
    --mc-white: #ffffff;
    --mc-gray: #aaaaaa;
    --mc-dark-gray: #555555;
    --mc-blue: #5555ff;
    --mc-inventory-bg: #c6c6c6;
    --mc-inventory-border: #373737;
    --mc-inventory-slot: #8b8b8b;
    --mc-tooltip-bg: #100010;
    --mc-tooltip-border: #28007f;
  }

  * { margin: 0; padding: 0; box-sizing: border-box; }

  html { scroll-behavior: smooth; }

  body {
    font-family: 'VT323', monospace;
    font-size: 20px;
    color: var(--mc-white);
    background: var(--mc-dark);
    line-height: 1.4;
    image-rendering: pixelated;
  }

  /* ==================== DIRT BACKGROUND PATTERN ==================== */
  .dirt-bg {
    background-color: #3b2712;
    background-image:
      repeating-conic-gradient(#4a3420 0% 25%, transparent 0% 50%) 0 0 / 8px 8px,
      repeating-conic-gradient(#352210 0% 25%, transparent 0% 50%) 4px 4px / 8px 8px;
    position: relative;
  }

  .dirt-bg::after {
    content: '';
    position: absolute;
    inset: 0;
    background: rgba(0,0,0,0.3);
    pointer-events: none;
  }

  /* ==================== HERO SECTION ==================== */
  .hero {
    text-align: center;
    padding: 60px 20px 50px;
    position: relative;
    overflow: hidden;
    border-bottom: 4px solid #000;
  }

  .hero-title {
    font-family: 'Press Start 2P', cursive;
    font-size: clamp(28px, 5vw, 52px);
    position: relative;
    z-index: 2;
    margin-bottom: 12px;
  }

  .hero-title .anti { color: var(--mc-red); text-shadow: 3px 3px 0 #8b0000; }
  .hero-title .loot { color: var(--mc-gold); text-shadow: 3px 3px 0 #b8860b; }
  .hero-title .steal { color: var(--mc-red); text-shadow: 3px 3px 0 #8b0000; }

  .hero-sub {
    font-family: 'Press Start 2P', cursive;
    font-size: clamp(8px, 1.5vw, 12px);
    color: var(--mc-gray);
    letter-spacing: 3px;
    position: relative;
    z-index: 2;
    margin-bottom: 20px;
  }

  .hero-version {
    display: inline-block;
    font-family: 'Press Start 2P', cursive;
    font-size: 10px;
    background: var(--mc-red);
    color: #fff;
    padding: 5px 14px;
    position: relative;
    z-index: 2;
    box-shadow: 4px 4px 0 #8b0000;
  }

  .hero-badges {
    display: flex;
    gap: 12px;
    justify-content: center;
    flex-wrap: wrap;
    margin-top: 20px;
    position: relative;
    z-index: 2;
  }

  .badge {
    font-family: 'Press Start 2P', cursive;
    font-size: 8px;
    padding: 6px 12px;
    border: 2px solid;
    display: flex;
    align-items: center;
    gap: 6px;
  }

  .badge-green { border-color: var(--mc-green); color: var(--mc-green); background: rgba(85,255,85,0.08); }
  .badge-gold { border-color: var(--mc-gold); color: var(--mc-gold); background: rgba(255,170,0,0.08); }
  .badge-aqua { border-color: var(--mc-aqua); color: var(--mc-aqua); background: rgba(85,255,255,0.08); }

  /* ==================== NAVIGATION ==================== */
  .nav {
    display: flex;
    justify-content: center;
    gap: 4px;
    padding: 10px 20px;
    background: #2a2a2a;
    border-bottom: 3px solid #000;
    flex-wrap: wrap;
    position: sticky;
    top: 0;
    z-index: 100;
  }

  .nav a {
    font-family: 'Press Start 2P', cursive;
    font-size: 9px;
    color: var(--mc-gray);
    text-decoration: none;
    padding: 8px 14px;
    background: #3a3a3a;
    border: 2px solid #555;
    transition: all 0.15s;
  }

  .nav a:hover {
    color: var(--mc-gold);
    border-color: var(--mc-gold);
    background: #4a3a1a;
    transform: translateY(-2px);
    box-shadow: 0 2px 0 #000;
  }

  /* ==================== MAIN LAYOUT ==================== */
  .main {
    max-width: 960px;
    margin: 0 auto;
    padding: 30px 20px 60px;
  }

  /* ==================== SECTION TITLE (Minecraft style) ==================== */
  .section {
    margin-bottom: 50px;
    scroll-margin-top: 60px;
  }

  .section-title {
    font-family: 'Press Start 2P', cursive;
    font-size: clamp(14px, 2.5vw, 20px);
    color: var(--mc-gold);
    text-shadow: 2px 2px 0 #5a3a00;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 3px solid #444;
    display: flex;
    align-items: center;
    gap: 12px;
  }

  .section-title .icon {
    font-size: 28px;
    line-height: 1;
  }

  /* ==================== MC TOOLTIP STYLE CARD ==================== */
  .mc-card {
    background: linear-gradient(180deg, #1a0a2e 0%, var(--mc-tooltip-bg) 100%);
    border: 3px solid var(--mc-tooltip-border);
    padding: 20px;
    margin-bottom: 16px;
    position: relative;
    box-shadow: inset 0 0 0 1px #5000a0, 0 4px 12px rgba(0,0,0,0.5);
  }

  .mc-card::before {
    content: '';
    position: absolute;
    top: 0; left: 0; right: 0;
    height: 1px;
    background: linear-gradient(90deg, transparent, #7b00ff88, transparent);
  }

  .mc-card-title {
    font-family: 'Silkscreen', cursive;
    font-size: 22px;
    font-weight: 700;
    margin-bottom: 8px;
  }

  .mc-card-desc {
    color: var(--mc-gray);
    font-size: 20px;
    line-height: 1.5;
  }

  .mc-card-desc .hl-green { color: var(--mc-green); }
  .mc-card-desc .hl-gold { color: var(--mc-gold); }
  .mc-card-desc .hl-red { color: var(--mc-red); }
  .mc-card-desc .hl-aqua { color: var(--mc-aqua); }
  .mc-card-desc .hl-yellow { color: var(--mc-yellow); }
  .mc-card-desc .hl-pink { color: var(--mc-pink); }
  .mc-card-desc .hl-white { color: var(--mc-white); }

  /* ==================== FEATURE GRID ==================== */
  .feature-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 16px;
  }

  .feature-card {
    background: linear-gradient(180deg, #1a0a2e 0%, var(--mc-tooltip-bg) 100%);
    border: 3px solid var(--mc-tooltip-border);
    padding: 18px;
    position: relative;
    box-shadow: inset 0 0 0 1px #5000a0;
    transition: transform 0.15s, box-shadow 0.15s;
  }

  .feature-card:hover {
    transform: translateY(-3px);
    box-shadow: inset 0 0 0 1px #5000a0, 0 6px 20px rgba(80, 0, 160, 0.3);
  }

  .feature-icon {
    font-size: 36px;
    margin-bottom: 8px;
    display: block;
  }

  .feature-name {
    font-family: 'Silkscreen', cursive;
    font-size: 18px;
    font-weight: 700;
    color: var(--mc-gold);
    margin-bottom: 6px;
  }

  .feature-desc {
    color: var(--mc-gray);
    font-size: 18px;
    line-height: 1.4;
  }

  /* ==================== INVENTORY STYLE TABLE ==================== */
  .inv-table {
    width: 100%;
    border-collapse: collapse;
    background: var(--mc-inventory-bg);
    box-shadow:
      inset 3px 3px 0 #fff,
      inset -3px -3px 0 #555,
      0 4px 12px rgba(0,0,0,0.5);
  }

  .inv-table th {
    font-family: 'Press Start 2P', cursive;
    font-size: 10px;
    background: var(--mc-inventory-border);
    color: var(--mc-white);
    padding: 12px 14px;
    text-align: left;
    border-bottom: 3px solid #000;
  }

  .inv-table td {
    padding: 10px 14px;
    border-bottom: 2px solid #aaa;
    color: #222;
    font-size: 18px;
    vertical-align: top;
  }

  .inv-table tr:nth-child(even) td {
    background: #b8b8b8;
  }

  .inv-table tr:hover td {
    background: #d4c890;
  }

  .perm-node {
    font-family: 'VT323', monospace;
    color: var(--mc-tooltip-bg);
    background: #e8c858;
    padding: 2px 8px;
    font-size: 18px;
    font-weight: bold;
    display: inline-block;
  }

  .perm-default {
    font-family: 'Press Start 2P', cursive;
    font-size: 8px;
    padding: 3px 8px;
    display: inline-block;
  }

  .perm-everyone { background: #5b8731; color: #fff; }
  .perm-op { background: #c44; color: #fff; }

  /* ==================== COMMAND BLOCKS ==================== */
  .cmd-block {
    background: #3a2a1a;
    border: 3px solid #6b4a2a;
    padding: 16px 20px;
    margin-bottom: 12px;
    display: flex;
    align-items: flex-start;
    gap: 14px;
    position: relative;
    box-shadow: inset 0 0 0 1px #8b6a3a;
  }

  .cmd-block::before {
    content: '>';
    font-family: 'Press Start 2P', cursive;
    font-size: 14px;
    color: var(--mc-gold);
    flex-shrink: 0;
    margin-top: 2px;
  }

  .cmd-text {
    font-family: 'VT323', monospace;
    font-size: 24px;
    color: var(--mc-white);
  }

  .cmd-text .cmd-name { color: var(--mc-gold); }
  .cmd-text .cmd-arg { color: var(--mc-green); }
  .cmd-desc {
    color: var(--mc-gray);
    font-size: 18px;
    margin-top: 4px;
  }

  /* ==================== CONFIG CODE BLOCK ==================== */
  .config-section {
    margin-bottom: 30px;
  }

  .config-header {
    font-family: 'Silkscreen', cursive;
    font-size: 18px;
    font-weight: 700;
    color: var(--mc-yellow);
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .config-header .dot {
    width: 8px;
    height: 8px;
    background: var(--mc-yellow);
    display: inline-block;
  }

  .config-block {
    background: #0a0a0a;
    border: 3px solid #333;
    padding: 16px 20px;
    font-family: 'VT323', monospace;
    font-size: 20px;
    line-height: 1.6;
    overflow-x: auto;
    box-shadow: inset 0 0 20px rgba(0,0,0,0.5);
    position: relative;
  }

  .config-block::before {
    content: 'config.yml';
    position: absolute;
    top: 0;
    right: 0;
    font-family: 'Press Start 2P', cursive;
    font-size: 7px;
    background: #333;
    color: #888;
    padding: 3px 8px;
  }

  .cfg-comment { color: #666; }
  .cfg-key { color: var(--mc-aqua); }
  .cfg-value-num { color: var(--mc-green); }
  .cfg-value-str { color: var(--mc-gold); }
  .cfg-value-bool { color: var(--mc-pink); }
  .cfg-indent { color: #444; }

  .config-explain {
    background: #1a1020;
    border-left: 4px solid var(--mc-purple);
    padding: 14px 18px;
    margin-top: 10px;
    color: var(--mc-gray);
    font-size: 18px;
    line-height: 1.5;
  }

  .config-explain strong {
    color: var(--mc-white);
    font-weight: normal;
  }

  .config-examples {
    margin-top: 10px;
  }

  .config-example {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 4px 0;
  }

  .config-example .ex-val {
    font-family: 'VT323', monospace;
    color: var(--mc-green);
    background: #0a0a0a;
    padding: 2px 10px;
    border: 1px solid #333;
    min-width: 120px;
    font-size: 20px;
  }

  .config-example .ex-desc {
    color: var(--mc-gray);
    font-size: 18px;
  }

  /* ==================== COLOR CODE TABLE ==================== */
  .color-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
    gap: 6px;
    margin-top: 10px;
  }

  .color-chip {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 6px 10px;
    background: #0a0a0a;
    border: 2px solid #333;
    font-size: 18px;
  }

  .color-chip .swatch {
    width: 16px;
    height: 16px;
    flex-shrink: 0;
    border: 1px solid #555;
  }

  .color-chip .code {
    color: var(--mc-gray);
  }

  /* ==================== SCENARIO / EXAMPLE BLOCKS ==================== */
  .scenario {
    background: linear-gradient(180deg, #0a1a0a 0%, #0a0a0a 100%);
    border: 3px solid #2a5a2a;
    padding: 20px;
    margin-bottom: 16px;
    position: relative;
    box-shadow: inset 0 0 0 1px #3a8a3a44;
  }

  .scenario-title {
    font-family: 'Silkscreen', cursive;
    font-size: 18px;
    font-weight: 700;
    color: var(--mc-green);
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .scenario-step {
    display: flex;
    gap: 10px;
    padding: 6px 0;
    color: var(--mc-gray);
    font-size: 19px;
  }

  .scenario-step .step-num {
    color: var(--mc-green);
    font-family: 'Press Start 2P', cursive;
    font-size: 9px;
    min-width: 24px;
    padding-top: 4px;
  }

  .scenario-step .hl { color: var(--mc-white); }

  /* ==================== HOLOGRAM PREVIEW ==================== */
  .holo-preview {
    display: flex;
    justify-content: center;
    gap: 30px;
    flex-wrap: wrap;
    padding: 30px 0;
  }

  .holo-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 6px;
  }

  .holo-text {
    font-family: 'Press Start 2P', cursive;
    font-size: 10px;
    padding: 6px 14px;
    border: 2px solid;
    background: rgba(0,0,0,0.6);
    white-space: nowrap;
  }

  .holo-green { color: var(--mc-green); border-color: #55ff5544; text-shadow: 0 0 10px rgba(85,255,85,0.4); }
  .holo-yellow { color: var(--mc-yellow); border-color: #ffff5544; text-shadow: 0 0 10px rgba(255,255,85,0.4); }
  .holo-red { color: var(--mc-red); border-color: #ff555544; text-shadow: 0 0 10px rgba(255,85,85,0.4); animation: blink 1s infinite; }

  @keyframes blink { 0%,100%{opacity:1} 50%{opacity:0.5} }

  .holo-label {
    font-family: 'Press Start 2P', cursive;
    font-size: 7px;
    color: #555;
  }

  .holo-arrow {
    font-size: 24px;
    color: #444;
    align-self: center;
    margin-bottom: 16px;
  }

  /* ==================== CHAT MESSAGE PREVIEW ==================== */
  .chat-preview {
    background: #0a0a0a;
    border: 3px solid #333;
    padding: 14px 18px;
    margin-top: 10px;
    font-family: 'VT323', monospace;
    font-size: 22px;
    line-height: 1.6;
  }

  .chat-line {
    padding: 2px 0;
  }

  /* ==================== INSTALLATION STEPS ==================== */
  .install-steps {
    counter-reset: step;
  }

  .install-step {
    display: flex;
    gap: 16px;
    padding: 14px 0;
    border-bottom: 2px dashed #333;
    align-items: flex-start;
  }

  .install-step::before {
    counter-increment: step;
    content: counter(step);
    font-family: 'Press Start 2P', cursive;
    font-size: 14px;
    color: var(--mc-dark);
    background: var(--mc-gold);
    min-width: 34px;
    height: 34px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    box-shadow: 3px 3px 0 #5a3a00;
  }

  .install-step-text {
    font-size: 20px;
    color: var(--mc-white);
    padding-top: 4px;
  }

  .install-step-text .path {
    color: var(--mc-gold);
    background: #1a1a1a;
    padding: 1px 8px;
    border: 1px solid #333;
  }

  /* ==================== FAQ ==================== */
  .faq-item {
    margin-bottom: 16px;
  }

  .faq-q {
    font-family: 'Silkscreen', cursive;
    font-size: 18px;
    font-weight: 700;
    color: var(--mc-aqua);
    cursor: pointer;
    padding: 10px 14px;
    background: #0a1a2a;
    border: 2px solid #1a3a5a;
    display: flex;
    align-items: center;
    gap: 10px;
    transition: background 0.15s;
  }

  .faq-q:hover { background: #0a2a3a; }

  .faq-q::before {
    content: 'Q';
    font-family: 'Press Start 2P', cursive;
    font-size: 10px;
    color: var(--mc-dark);
    background: var(--mc-aqua);
    width: 22px;
    height: 22px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }

  .faq-a {
    padding: 12px 14px 12px 46px;
    color: var(--mc-gray);
    font-size: 19px;
    background: #060d14;
    border: 2px solid #1a3a5a;
    border-top: none;
    line-height: 1.5;
  }

  /* ==================== FOOTER ==================== */
  .footer {
    text-align: center;
    padding: 30px 20px;
    border-top: 4px solid #000;
    position: relative;
  }

  .footer-text {
    font-family: 'Press Start 2P', cursive;
    font-size: 8px;
    color: #555;
    position: relative;
    z-index: 2;
  }

  /* ==================== SCROLLBAR ==================== */
  ::-webkit-scrollbar { width: 10px; }
  ::-webkit-scrollbar-track { background: #1a1a1a; }
  ::-webkit-scrollbar-thumb { background: #555; border: 2px solid #1a1a1a; }
  ::-webkit-scrollbar-thumb:hover { background: #777; }
</style>
</head>
<body>

<!-- ==================== HERO ==================== -->
<header class="hero dirt-bg">
  <div class="hero-title">
    <span class="anti">Anti</span><span class="loot">Loot</span><span class="steal">Steal</span>
  </div>

  <span class="hero-version">v1.0.0</span>
  <div class="hero-badges">
    <div class="badge badge-green">&#9889; Lightweight</div>
    <div class="badge badge-gold">&#9876; 1.8 - 1.21+</div>
    <div class="badge badge-aqua">&#9881; No Dependencies</div>
  </div>
</header>

<!-- ==================== NAVIGATION ==================== -->
<nav class="nav">
  <a href="#features">Features</a>
  <a href="#install">Install</a>
  <a href="#commands">Commands</a>
  <a href="#permissions">Permissions</a>
  <a href="#config">Config</a>
  <a href="#hologram">Hologram</a>
  <a href="#examples">Examples</a>
  <a href="#faq">FAQ</a>
</nav>

<main class="main">

<!-- ==================== FEATURES ==================== -->
<section class="section" id="features">
  <h2 class="section-title"><span class="icon">&#9733;</span> Features</h2>

  <div class="feature-grid">
    <div class="feature-card">
      <span class="feature-icon">&#128274;</span>
      <div class="feature-name">Loot Protection</div>
      <div class="feature-desc">When a player kills a mob or player, dropped items are locked to the killer for a configurable time. Nobody else can steal them.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#9201;</span>
      <div class="feature-name">Configurable Timer</div>
      <div class="feature-desc">Set the protection duration to any value in seconds via config.yml. Default is 15 seconds.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#127752;</span>
      <div class="feature-name">Color Hologram</div>
      <div class="feature-desc">A floating, color-changing countdown appears above each protected item. Green &rarr; Yellow &rarr; Red as time runs out.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#128737;</span>
      <div class="feature-name">Entity Filtering</div>
      <div class="feature-desc">Choose which kill types trigger protection: Player kills (PvP), Zombie kills, and Animal kills. Each toggleable in config.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#127758;</span>
      <div class="feature-name">Per-World Control</div>
      <div class="feature-desc">Enable or disable protection in specific worlds. Whitelist or blacklist worlds as needed.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#128273;</span>
      <div class="feature-name">Permission Based</div>
      <div class="feature-desc">Full permission support. Control who gets protection, who can bypass it, and who can use admin commands.</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#128172;</span>
      <div class="feature-name">Custom Messages</div>
      <div class="feature-desc">All player-facing messages are fully customizable with Minecraft color codes (&amp;a, &amp;c, &amp;e, etc.).</div>
    </div>
    <div class="feature-card">
      <span class="feature-icon">&#9889;</span>
      <div class="feature-name">Lightweight</div>
      <div class="feature-desc">No external dependencies. Uses O(1) lookups with ConcurrentHashMap. Minimal performance impact even with 100+ items.</div>
    </div>
  </div>
</section>

<!-- ==================== INSTALLATION ==================== -->
<section class="section" id="install">
  <h2 class="section-title"><span class="icon">&#128230;</span> Installation</h2>

  <div class="mc-card">
    <div class="install-steps">
      <div class="install-step">
        <div class="install-step-text">Download <span class="path">AntiLootSteal-1.0.0.jar</span></div>
      </div>
      <div class="install-step">
        <div class="install-step-text">Place it in your server's <span class="path">/plugins/</span> folder</div>
      </div>
      <div class="install-step">
        <div class="install-step-text">Restart or reload your server</div>
      </div>
      <div class="install-step">
        <div class="install-step-text">A default <span class="path">config.yml</span> will be generated in <span class="path">/plugins/AntiLootSteal/</span></div>
      </div>
      <div class="install-step">
        <div class="install-step-text">Edit the config to your liking, then run <span class="path">/antilootsteal reload</span></div>
      </div>
    </div>
  </div>
</section>

<!-- ==================== COMMANDS ==================== -->
<section class="section" id="commands">
  <h2 class="section-title"><span class="icon">&#9000;</span> Commands</h2>

  <div class="cmd-block">
    <div>
      <div class="cmd-text"><span class="cmd-name">/antilootsteal</span></div>
      <div class="cmd-desc">Shows plugin version and help information</div>
    </div>
  </div>

  <div class="cmd-block">
    <div>
      <div class="cmd-text"><span class="cmd-name">/antilootsteal</span> <span class="cmd-arg">reload</span></div>
      <div class="cmd-desc">Reloads config.yml without restarting the server. Requires <span style="color:var(--mc-gold)">antilootsteal.admin</span></div>
    </div>
  </div>

  <div class="cmd-block">
    <div>
      <div class="cmd-text"><span class="cmd-name">/als</span> <span class="cmd-arg">reload</span></div>
      <div class="cmd-desc">Short alias &mdash; same as /antilootsteal reload</div>
    </div>
  </div>
</section>

<!-- ==================== PERMISSIONS ==================== -->
<section class="section" id="permissions">
  <h2 class="section-title"><span class="icon">&#128273;</span> Permissions</h2>

  <table class="inv-table">
    <thead>
      <tr>
        <th>Permission</th>
        <th>Description</th>
        <th>Default</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><span class="perm-node">antilootsteal.protect</span></td>
        <td>Loot from your kills is protected by the timer. Without this, kills drop items normally.</td>
        <td><span class="perm-default perm-everyone">Everyone</span></td>
      </tr>
      <tr>
        <td><span class="perm-node">antilootsteal.bypass</span></td>
        <td>Bypass all loot protection. Pick up any protected loot instantly, ignoring the timer.</td>
        <td><span class="perm-default perm-op">OP Only</span></td>
      </tr>
      <tr>
        <td><span class="perm-node">antilootsteal.admin</span></td>
        <td>Access to /antilootsteal reload and all admin commands.</td>
        <td><span class="perm-default perm-op">OP Only</span></td>
      </tr>
    </tbody>
  </table>

  <div class="mc-card" style="margin-top: 16px;">
    <div class="mc-card-title" style="color: var(--mc-aqua);">LuckPerms Examples</div>
    <div class="config-block" style="margin-top: 10px;">
      <span class="cfg-comment"># Give all players loot protection (default)</span><br>
      <span class="cfg-value-str">/lp group default permission set antilootsteal.protect true</span><br><br>
      <span class="cfg-comment"># VIP players can bypass protection</span><br>
      <span class="cfg-value-str">/lp group vip permission set antilootsteal.bypass true</span><br><br>
      <span class="cfg-comment"># Only admins can reload</span><br>
      <span class="cfg-value-str">/lp group admin permission set antilootsteal.admin true</span>
    </div>
  </div>
</section>

<!-- ==================== CONFIG GUIDE ==================== -->
<section class="section" id="config">
  <h2 class="section-title"><span class="icon">&#9881;</span> Configuration Guide</h2>

  <!-- Protection Duration -->
  <div class="config-section">
    <div class="config-header"><span class="dot"></span> Protection Duration</div>
    <div class="config-block">
      <span class="cfg-comment"># Protection timer in seconds</span><br>
      <span class="cfg-key">protection-duration:</span> <span class="cfg-value-num">15</span>
    </div>
    <div class="config-explain">
      How long items are protected after a kill. Only the killer can pick them up during this time.
      <div class="config-examples" style="margin-top: 8px;">
        <div class="config-example"><span class="ex-val">5</span><span class="ex-desc">Very short, fast-paced servers</span></div>
        <div class="config-example"><span class="ex-val">15</span><span class="ex-desc">Default, balanced for most servers</span></div>
        <div class="config-example"><span class="ex-val">30</span><span class="ex-desc">Long protection, strict anti-steal</span></div>
        <div class="config-example"><span class="ex-val">60</span><span class="ex-desc">1 minute, maximum protection</span></div>
      </div>
    </div>
  </div>

  <!-- World Filtering -->
  <div class="config-section">
    <div class="config-header"><span class="dot"></span> World Filtering</div>
    <div class="config-block">
      <span class="cfg-comment"># Worlds where protection is enabled (empty = all worlds)</span><br>
      <span class="cfg-key">enabled-worlds:</span> <span class="cfg-value-str">[]</span><br><br>
      <span class="cfg-comment"># Worlds where protection is explicitly disabled</span><br>
      <span class="cfg-key">disabled-worlds:</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-value-str">- world_creative</span>
    </div>
    <div class="config-explain">
      <strong>enabled-worlds:</strong> If empty <span style="color:var(--mc-green)">[]</span>, protection works in ALL worlds.
      If you list world names, protection ONLY works in those worlds.<br><br>
      <strong>disabled-worlds:</strong> Worlds listed here will never have loot protection. This takes priority over enabled-worlds.<br><br>
      <strong>Example A</strong> &mdash; Everywhere except creative:<br>
      &nbsp;&nbsp;enabled-worlds: <span style="color:var(--mc-green)">[]</span> &nbsp;/&nbsp; disabled-worlds: <span style="color:var(--mc-gold)">world_creative</span><br><br>
      <strong>Example B</strong> &mdash; Only in survival and nether:<br>
      &nbsp;&nbsp;enabled-worlds: <span style="color:var(--mc-gold)">world, world_nether</span> &nbsp;/&nbsp; disabled-worlds: <span style="color:var(--mc-green)">[]</span>
    </div>
  </div>

  <!-- Protected Entities -->
  <div class="config-section">
    <div class="config-header"><span class="dot"></span> Protected Entity Types</div>
    <div class="config-block">
      <span class="cfg-key">protected-entities:</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">players:</span> <span class="cfg-value-bool">true</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">zombies:</span> <span class="cfg-value-bool">true</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">animals:</span> <span class="cfg-value-bool">true</span>
    </div>
    <div class="config-explain">
      Toggle which entity types trigger loot protection when killed by a player:<br><br>
      <strong>players</strong> &mdash; PvP kills (Player drops)<br>
      <strong>zombies</strong> &mdash; Zombie, Husk, Drowned, Zombie Villager<br>
      <strong>animals</strong> &mdash; Cow, Pig, Sheep, Chicken, Horse, etc.<br><br>
      <strong>PvP server:</strong> players: <span style="color:var(--mc-green)">true</span>, zombies: <span style="color:var(--mc-red)">false</span>, animals: <span style="color:var(--mc-red)">false</span><br>
      <strong>PvE server:</strong> players: <span style="color:var(--mc-red)">false</span>, zombies: <span style="color:var(--mc-green)">true</span>, animals: <span style="color:var(--mc-green)">true</span>
    </div>
  </div>

  <!-- Messages -->
  <div class="config-section">
    <div class="config-header"><span class="dot"></span> Messages</div>
    <div class="config-block">
      <span class="cfg-key">messages:</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">loot-protected:</span> <span class="cfg-value-str">"&amp;c&amp;lAntiLootSteal &amp;8&gt; &amp;7This loot belongs to &amp;e{player}&amp;7! Wait &amp;e{time}s&amp;7."</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">loot-expired:</span> <span class="cfg-value-str">"&amp;a&amp;lAntiLootSteal &amp;8&gt; &amp;7Loot protection has expired."</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">reload-success:</span> <span class="cfg-value-str">"&amp;a&amp;lAntiLootSteal &amp;8&gt; &amp;7Config reloaded successfully."</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">no-permission:</span> <span class="cfg-value-str">"&amp;c&amp;lAntiLootSteal &amp;8&gt; &amp;7You don't have permission."</span>
    </div>
    <div class="config-explain">
      All messages support <strong>&amp; color codes</strong> and these placeholders:<br><br>
      <strong>{player}</strong> &mdash; Name of the killer (loot owner)<br>
      <strong>{time}</strong> &mdash; Seconds remaining on the protection timer<br><br>
      <strong>Chat preview of loot-protected message:</strong>
      <div class="chat-preview" style="margin-top: 8px;">
        <div class="chat-line"><span style="color:var(--mc-red);font-weight:bold;">AntiLootSteal</span> <span style="color:#555;">&gt;</span> <span style="color:var(--mc-gray);">This loot belongs to</span> <span style="color:var(--mc-yellow);">Steve</span><span style="color:var(--mc-gray);">! Wait</span> <span style="color:var(--mc-yellow);">12s</span><span style="color:var(--mc-gray);">.</span></div>
      </div>
    </div>
  </div>

  <!-- Color Codes Reference -->
  <div class="config-section">
    <div class="config-header"><span class="dot"></span> Color Code Reference</div>
    <div class="color-grid">
      <div class="color-chip"><span class="swatch" style="background:#000"></span><span class="code">&amp;0 Black</span></div>
      <div class="color-chip"><span class="swatch" style="background:#0000aa"></span><span class="code">&amp;1 Dark Blue</span></div>
      <div class="color-chip"><span class="swatch" style="background:#00aa00"></span><span class="code">&amp;2 Dark Green</span></div>
      <div class="color-chip"><span class="swatch" style="background:#00aaaa"></span><span class="code">&amp;3 Dark Aqua</span></div>
      <div class="color-chip"><span class="swatch" style="background:#aa0000"></span><span class="code">&amp;4 Dark Red</span></div>
      <div class="color-chip"><span class="swatch" style="background:#aa00aa"></span><span class="code">&amp;5 Purple</span></div>
      <div class="color-chip"><span class="swatch" style="background:#ffaa00"></span><span class="code">&amp;6 Gold</span></div>
      <div class="color-chip"><span class="swatch" style="background:#aaa"></span><span class="code">&amp;7 Gray</span></div>
      <div class="color-chip"><span class="swatch" style="background:#555"></span><span class="code">&amp;8 Dark Gray</span></div>
      <div class="color-chip"><span class="swatch" style="background:#5555ff"></span><span class="code">&amp;9 Blue</span></div>
      <div class="color-chip"><span class="swatch" style="background:#55ff55"></span><span class="code">&amp;a Green</span></div>
      <div class="color-chip"><span class="swatch" style="background:#55ffff"></span><span class="code">&amp;b Aqua</span></div>
      <div class="color-chip"><span class="swatch" style="background:#ff5555"></span><span class="code">&amp;c Red</span></div>
      <div class="color-chip"><span class="swatch" style="background:#ff55ff"></span><span class="code">&amp;d Pink</span></div>
      <div class="color-chip"><span class="swatch" style="background:#ffff55"></span><span class="code">&amp;e Yellow</span></div>
      <div class="color-chip"><span class="swatch" style="background:#fff"></span><span class="code">&amp;f White</span></div>
    </div>
    <div class="color-grid" style="margin-top: 8px; grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));">
      <div class="color-chip"><span class="code" style="font-weight:bold;">&amp;l</span><span class="code">Bold</span></div>
      <div class="color-chip"><span class="code" style="font-style:italic;">&amp;o</span><span class="code">Italic</span></div>
      <div class="color-chip"><span class="code" style="text-decoration:underline;">&amp;n</span><span class="code">Underline</span></div>
      <div class="color-chip"><span class="code" style="text-decoration:line-through;">&amp;m</span><span class="code">Strikethrough</span></div>
      <div class="color-chip"><span class="code">&amp;k</span><span class="code">Obfuscated</span></div>
      <div class="color-chip"><span class="code">&amp;r</span><span class="code">Reset</span></div>
    </div>
  </div>
</section>

<!-- ==================== HOLOGRAM SECTION ==================== -->
<section class="section" id="hologram">
  <h2 class="section-title"><span class="icon">&#127752;</span> Hologram System</h2>

  <div class="mc-card">
    <div class="mc-card-title" style="color: var(--mc-yellow);">Hologram Config</div>
    <div class="config-block" style="margin-top: 10px;">
      <span class="cfg-key">hologram:</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">enabled:</span> <span class="cfg-value-bool">true</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">y-offset:</span> <span class="cfg-value-num">1.2</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">color-high:</span> <span class="cfg-value-str">"&amp;a"</span> <span class="cfg-comment"># Green</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">color-medium:</span> <span class="cfg-value-str">"&amp;e"</span> <span class="cfg-comment"># Yellow</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">color-low:</span> <span class="cfg-value-str">"&amp;c"</span> <span class="cfg-comment"># Red</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">medium-threshold:</span> <span class="cfg-value-num">10</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">low-threshold:</span> <span class="cfg-value-num">5</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">format:</span> <span class="cfg-value-str">"{color}&amp;l{time}s &amp;7| &amp;f{player}'s loot"</span><br>
      <span class="cfg-indent">&nbsp;&nbsp;</span><span class="cfg-key">update-interval:</span> <span class="cfg-value-num">20</span>
    </div>
  </div>

  <div class="mc-card">
    <div class="mc-card-title" style="color: var(--mc-green);">Live Preview</div>
    <div class="mc-card-desc">This is how the hologram looks in-game as the timer counts down:</div>

    <div class="holo-preview">
      <div class="holo-item">
        <div class="holo-text holo-green">15s | Steve's loot</div>
        <div class="holo-label">HIGH (15s-11s)</div>
      </div>
      <div class="holo-arrow">&#10132;</div>
      <div class="holo-item">
        <div class="holo-text holo-yellow">8s | Steve's loot</div>
        <div class="holo-label">MEDIUM (10s-6s)</div>
      </div>
      <div class="holo-arrow">&#10132;</div>
      <div class="holo-item">
        <div class="holo-text holo-red">3s | Steve's loot</div>
        <div class="holo-label">LOW (5s-0s)</div>
      </div>
    </div>
  </div>

  <div class="config-explain">
    <strong>enabled</strong> &mdash; Toggle holograms on/off. Protection still works when disabled.<br><br>
    <strong>y-offset</strong> &mdash; Height above the item (in blocks). <span style="color:var(--mc-green)">1.2</span> is default.<br><br>
    <strong>color-high / medium / low</strong> &mdash; Color codes for each phase of the countdown.<br><br>
    <strong>medium-threshold</strong> &mdash; Seconds remaining when color changes from green &rarr; yellow.<br>
    <strong>low-threshold</strong> &mdash; Seconds remaining when color changes from yellow &rarr; red.<br><br>
    <strong>format placeholders:</strong><br>
    &nbsp;&nbsp;<span style="color:var(--mc-aqua)">{color}</span> &mdash; Auto-replaced with the current phase color<br>
    &nbsp;&nbsp;<span style="color:var(--mc-aqua)">{time}</span> &mdash; Seconds remaining<br>
    &nbsp;&nbsp;<span style="color:var(--mc-aqua)">{player}</span> &mdash; Killer's name<br><br>
    <strong>Format examples:</strong>
    <div class="chat-preview" style="margin-top: 8px;">
      <div class="chat-line" style="color:var(--mc-gray);">{color}&amp;l{time}s &amp;7| &amp;f{player}'s loot &rarr; <span style="color:var(--mc-green);font-weight:bold;">12s</span> <span style="color:var(--mc-gray);">|</span> <span style="color:#fff;">Steve's loot</span></div>
      <div class="chat-line" style="color:var(--mc-gray);">&amp;f[&amp;c{time}s&amp;f] {player} &rarr; <span style="color:#fff;">[</span><span style="color:var(--mc-red);">12s</span><span style="color:#fff;">] Steve</span></div>
      <div class="chat-line" style="color:var(--mc-gray);">{color}&amp;l&gt;&gt; &amp;f{player} &amp;7- {color}{time}s &rarr; <span style="color:var(--mc-yellow);font-weight:bold;">&gt;&gt;</span> <span style="color:#fff;">Steve</span> <span style="color:var(--mc-gray);">-</span> <span style="color:var(--mc-yellow);">8s</span></div>
    </div>
    <br>
    <strong>update-interval</strong> &mdash; Refresh rate in ticks. <span style="color:var(--mc-green)">20</span> = 1 second (recommended). <span style="color:var(--mc-yellow)">10</span> = 0.5s (smoother).
  </div>
</section>

<!-- ==================== EXAMPLES ==================== -->
<section class="section" id="examples">
  <h2 class="section-title"><span class="icon">&#128214;</span> Usage Examples</h2>

  <div class="scenario">
    <div class="scenario-title">&#9876; Scenario 1: Player kills a Zombie</div>
    <div class="scenario-step"><span class="step-num">1</span><span><span class="hl">Steve</span> kills a Zombie. It drops <span style="color:var(--mc-gold)">Rotten Flesh</span> and an <span style="color:var(--mc-aqua)">Iron Sword</span>.</span></div>
    <div class="scenario-step"><span class="step-num">2</span><span>Both items are now <span style="color:var(--mc-red)">protected for 15 seconds</span>.</span></div>
    <div class="scenario-step"><span class="step-num">3</span><span>A <span style="color:var(--mc-green)">green hologram</span> appears: <span style="color:var(--mc-green);font-weight:bold;">15s | Steve's loot</span></span></div>
    <div class="scenario-step"><span class="step-num">4</span><span>The timer counts down. At 10s it turns <span style="color:var(--mc-yellow)">yellow</span>, at 5s it turns <span style="color:var(--mc-red)">red</span>.</span></div>
    <div class="scenario-step"><span class="step-num">5</span><span><span class="hl">Steve</span> can pick up items at any time during the timer.</span></div>
    <div class="scenario-step"><span class="step-num">6</span><span>If <span class="hl">Alex</span> tries to pick them up, she is <span style="color:var(--mc-red)">denied</span> and sees a message.</span></div>
    <div class="scenario-step"><span class="step-num">7</span><span>After 15s, the hologram disappears and items become <span style="color:var(--mc-green)">free for everyone</span>.</span></div>
  </div>

  <div class="scenario">
    <div class="scenario-title">&#9876; Scenario 2: PvP Kill</div>
    <div class="scenario-step"><span class="step-num">1</span><span><span class="hl">Alex</span> kills <span class="hl">Steve</span> in PvP combat.</span></div>
    <div class="scenario-step"><span class="step-num">2</span><span>Steve's entire dropped inventory (armor, weapons, food) is <span style="color:var(--mc-red)">locked to Alex</span>.</span></div>
    <div class="scenario-step"><span class="step-num">3</span><span>No other player can swoop in and steal the loot. Alex has full priority.</span></div>
    <div class="scenario-step"><span class="step-num">4</span><span>This prevents <span style="color:var(--mc-gold)">"loot vultures"</span> from stealing kills in PvP.</span></div>
  </div>

  <div class="scenario">
    <div class="scenario-title">&#128737; Scenario 3: Admin Bypass</div>
    <div class="scenario-step"><span class="step-num">1</span><span><span class="hl">Steve</span> kills a Cow. The Raw Beef is protected.</span></div>
    <div class="scenario-step"><span class="step-num">2</span><span><span class="hl">Admin</span> has the <span style="color:var(--mc-gold)">antilootsteal.bypass</span> permission.</span></div>
    <div class="scenario-step"><span class="step-num">3</span><span>Admin walks over the items and picks them up <span style="color:var(--mc-green)">instantly</span>, ignoring the timer.</span></div>
  </div>

  <div class="scenario">
    <div class="scenario-title">&#9881; Scenario 4: Selective Protection</div>
    <div class="scenario-step"><span class="step-num">1</span><span>Config: <span style="color:var(--mc-aqua)">animals:</span> <span style="color:var(--mc-red)">false</span>, <span style="color:var(--mc-aqua)">players:</span> <span style="color:var(--mc-green)">true</span></span></div>
    <div class="scenario-step"><span class="step-num">2</span><span>Steve kills a Sheep &rarr; Drops are <span style="color:var(--mc-green)">free for anyone</span> (no protection).</span></div>
    <div class="scenario-step"><span class="step-num">3</span><span>Steve kills a Player &rarr; Drops <span style="color:var(--mc-red)">are protected</span> with full timer + hologram.</span></div>
  </div>
</section>

<!-- ==================== FAQ ==================== -->
<section class="section" id="faq">
  <h2 class="section-title"><span class="icon">&#10067;</span> FAQ</h2>

  <div class="faq-item">
    <div class="faq-q">Does it work with Paper / Purpur?</div>
    <div class="faq-a">Yes. Paper, Purpur, and all Spigot forks are fully compatible.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Does it work with Minecraft 1.8?</div>
    <div class="faq-a">Yes. Compiled against Spigot 1.8.8 API with Java 8 target. Works on all versions from 1.8.8 to the latest.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Will items despawn while protected?</div>
    <div class="faq-a">Items follow vanilla despawn rules (5 minutes). Protection does not affect despawn timers.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">What if the killer disconnects?</div>
    <div class="faq-a">Loot stays protected until the timer expires, then becomes free. This is intended behavior.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Can protected items merge with others?</div>
    <div class="faq-a">No. The plugin prevents merging of protected items to ensure accurate tracking. After expiry, they merge normally.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Does it protect experience orbs?</div>
    <div class="faq-a">No. Only item drops are protected. Experience drops normally for whoever picks them up.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">What happens on server restart?</div>
    <div class="faq-a">All active protections are cleared on shutdown (holograms are properly removed). New kills after restart create fresh protections. Since timers are short-lived, this is by design.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Does the hologram lag the server?</div>
    <div class="faq-a">No. Each hologram is one invisible armor stand updated once per second. Even 100+ items have negligible impact.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Does it work with LuckPerms / PermissionsEx?</div>
    <div class="faq-a">Yes. All standard Bukkit-compatible permission plugins work out of the box.</div>
  </div>
  <div class="faq-item">
    <div class="faq-q">Can I disable holograms but keep protection?</div>
    <div class="faq-a">Yes. Set <strong>hologram: enabled: false</strong> in config.yml. Items will still be protected; players just won't see the floating text.</div>
  </div>
</section>

</main>

<!-- ==================== FOOTER ==================== -->
<footer class="footer dirt-bg">
  <div class="footer-text">AntiLootSteal v1.0.0 &bull; Spigot 1.8.8 - 1.21+</div>
</footer>

</body>
</html>
