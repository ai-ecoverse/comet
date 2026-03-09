# Comet — Experience Intelligence Skill

Comet analyzes websites and generates visual HTML reports inside SLICC. No backend, no deployment — you are the analyst, the generator, and the chat.

## First Run — Scaffold

Before generating any report, check if `/workspace/comet/reports/comet.css` exists. If not, run the scaffold command:

```bash
comet-scaffold
```

This creates the workspace structure with the design system, hub, and empty manifest.

## What You Do

When the user asks you to analyze a website:

1. **Collect data** — Crawl the site (fetch sitemaps, query indexes, pages, metadata) using `curl` or the `browser` tool. Save raw JSON to `/workspace/comet/data/{site}/`
2. **Analyze** — Process the data, cross-reference sources, identify issues and opportunities
3. **Generate reports** — Write self-contained HTML report files to `/workspace/comet/reports/`
4. **Update the manifest** — Read `/workspace/comet/reports/manifest.json`, add/update the entry, write it back
5. **Preview** — Use the `serve` action on `/workspace/comet/reports/` and screenshot the result

## Report HTML Template

Every report MUST use this structure:

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>{Report Title} — Comet</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@400;600;700;900&family=Source+Code+Pro:wght@400;500&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="comet.css">
  <style>/* report-specific overrides only */</style>
</head>
<body>
  <nav class="report-nav">
    <a href="hub.html" class="nav-hub">&larr; Hub</a>
    <span class="nav-sep">|</span>
    <!-- related report links as .nav-related pills: <a href="{id}.html" class="nav-related">{title}</a> -->
  </nav>

  <header class="report-header">
    <div class="report-header-inner">
      <div>
        <h1>{Title}</h1>
        <div class="subtitle">{Subtitle}</div>
      </div>
      <div style="display:flex;align-items:center;gap:16px;">
        <div class="report-branding">Comet</div>
        <span class="report-date">{YYYY-MM-DD}</span>
      </div>
    </div>
  </header>

  <div class="container">
    <!-- Jump nav, sections, stat cards, tables, charts -->
  </div>
</body>
</html>
```

## CSS Classes (from comet.css)

**Layout:** `.container`, `.two-col`, `.three-col`
**Sections:** `.section`, `.section-title`, `.section-desc`
**Stats:** `.summary-grid`, `.stat-card` (`.accent`/`.positive`/`.warning`/`.critical`/`.info`), `.stat-value`, `.stat-label`
**Cards:** `.card`, `.card h3`, `.card p`, `.card ul/li`
**Tables:** `.table-wrap`, `.data-table`, `.data-table th/td`
**Bars:** `.bar-chart`, `.bar-row`, `.bar-label`, `.bar-track`, `.bar-fill` (`.green`/`.blue`/`.amber`/`.red`/`.purple`/`.cyan`), `.bar-count`
**Badges:** `.badge`, `.badge-pass`/`.badge-warn`/`.badge-fail`/`.badge-info`/`.badge-neutral`
**Grades:** `.grade-grid`, `.grade-card`, `.grade-letter` (`.a`/`.b`/`.c`/`.d`/`.f`), `.grade-aspect`, `.grade-detail`
**Quotes:** `.quote-block` (`.pass`/`.warn`/`.fail`), `.quote-text`, `.quote-source`
**Navigation:** `.jump-nav`, `.jump-nav-title`, `.jump-nav-links`
**Validation:** `.validation-stamp`

## Manifest Entry Format

`/workspace/comet/reports/manifest.json` is a JSON array. Each report entry:

```json
{
  "id": "site-report-name",
  "title": "Report Title",
  "date": "2026-03-09",
  "category": "audit|brand|performance|optimization",
  "summary": "One-paragraph summary of key findings.",
  "related": ["other-report-id"],
  "sections": [
    { "id": "section-id", "label": "Human-readable section description" }
  ]
}
```

## Rules

- **All asset paths are relative** — `comet.css`, `hub.html`, report links. Everything lives in `/workspace/comet/reports/`.
- **Self-contained HTML** — every report works standalone. No external JS.
- **Cross-validate** — every report must be independently verified. Spot-check HTTP responses, recount raw data, sample content. Add a `.validation-stamp` at the bottom.
- **Data-rich** — stat cards for metrics, bar charts for distributions, tables for detail. Informative, not decorative.
- **Scoops for parallelism** — when analyzing multiple aspects, create scoops to run analyses in parallel. Each scoop writes its report + data, then the cone updates the manifest.
- **Preview after generation** — always `serve` `/workspace/comet/reports/` and screenshot to verify rendering.

## Trigger Phrases

- "analyze {url}", "audit {url}", "generate a report on {url}"
- "run comet on {url}", "check the SEO/brand/performance of {url}"
