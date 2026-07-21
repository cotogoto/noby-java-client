# AGENTS.md

## Project Overview

`noby-java-client` is a Java client library for the external NOBY API. It is a single Maven JAR module; this repository contains no server controllers, UI, database, or deployment manifest. Treat `NobyClient`, its constructor and `exec` method, and the model properties as public compatibility surfaces.

Read `docs/ai-instructions.md` for detailed project-specific safety and decision rules. This file is the concise, tool-neutral entry point shared by coding agents.

## Commands

Use a Java 11 environment, as configured by `pom.xml` and Eclipse project settings.

```powershell
mvn --version
mvn clean verify
git diff --check
git status --short
```

The current manual `NobyClientTest` is a `main` program, not an automated JUnit test. Never insert a real API key into it. Java 21 with the pinned Lombok 1.18.28 is known to fail compilation; report the environment mismatch instead of treating it as a source failure.

## Code Style

- Keep Java 11 source compatibility.
- Follow the existing package layout and four-space indentation.
- Preserve existing public APIs unless the user explicitly approves a compatibility change.
- Follow existing JavaDoc and `final` usage patterns in touched code.
- Keep HTTP parameters and JSON fields aligned with verified NOBY API behavior; do not invent API contracts.

## Testing

- Add repeatable automated tests for changed behavior where practical.
- Do not call the production NOBY API from routine tests.
- Cover normal, HTTP/API error, null/empty, encoding, and boundary behavior relevant to the change.
- Report commands that could not run and why. Do not claim tests passed when Maven found no automated tests.

## Git Workflow

- Inspect `git status` before editing and preserve unrelated user changes.
- Keep changes scoped and review `git diff` before completion.
- Do not commit, tag, push, release, or deploy unless explicitly requested.
- Do not commit `target`, IDE-local files, credentials, or generated release state.

## Boundaries

- Never expose or store `app_key`, `mail`, `pass`, `token`, personal conversation text, or location data.
- Do not log full request URLs; they can contain secrets and personal data.
- Do not change dependencies, Java level, endpoint URL, authentication, release configuration, or public API without explicit scope and compatibility review.
- Do not infer production procedures, external API guarantees, or data-retention rules from this client repository.
- Code, database migrations, dependencies, and CI configuration remain unchanged for documentation-only tasks.

## Workflow

1. Read this file, `README.md`, `pom.xml`, relevant source, and the matching documents under `docs`.
2. Separate verified facts from assumptions and list unresolved items.
3. Make the smallest in-scope change using existing patterns.
4. Check security and public compatibility.
5. Run the applicable commands and report exact results and limitations.

