// Comet scaffold — creates workspace structure for reports
// Run: comet-scaffold

const dirs = [
  '/workspace/comet/reports',
  '/workspace/comet/data',
  '/workspace/comet/scripts',
];

for (const dir of dirs) {
  if (!await fs.exists(dir)) {
    await fs.mkdir(dir, true);
    console.log(`Created ${dir}`);
  }
}

// Copy skill assets to workspace if not present
const skillDir = process.env.__SKILL_DIR || '/workspace/skills/comet';

const assets = [
  { src: `${skillDir}/reports/comet.css`, dest: '/workspace/comet/reports/comet.css' },
  { src: `${skillDir}/reports/hub.html`, dest: '/workspace/comet/reports/hub.html' },
  { src: `${skillDir}/reports/manifest.json`, dest: '/workspace/comet/reports/manifest.json' },
];

for (const { src, dest } of assets) {
  if (await fs.exists(dest)) {
    console.log(`Exists: ${dest}`);
    continue;
  }
  try {
    const content = await fs.readFile(src);
    await fs.writeFile(dest, content);
    console.log(`Copied: ${dest}`);
  } catch (e) {
    console.error(`Could not copy ${src}: ${e.message}`);
  }
}

console.log('\nComet scaffold ready. Reports go in /workspace/comet/reports/');
