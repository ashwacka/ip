# AI Use Declaration

This file records use of AI tools in the ip (Wacka) project.

## Tool used

- **Cursor** (AI-assisted editing and chat)

## Use by increment / task

| Increment / task | How AI was used |
|------------------|------------------|
| **A-Assertions** | Added 8+ assertions across wacka package (Wacka, TaskList, Parser, Storage, Ui, Main, MainWindow, DialogBox). |
| **Code quality** | Refactored long methods: `Storage.parseTaskFromFile` split into smaller helpers; `Parser.parse` split into command-specific methods; reduced nesting in `TaskList` constructor. |
| **B-RecurringTasks** | Implemented recurring tasks: `RecurringTask` class, parser for `recur` command, storage load/save, replace-on-mark logic; fixed bugs (imports, constructor, error handling). |
| **A-BetterGui** | Improved GUI: asymmetric user/bot bubbles, error highlighting, CSS styling, resizable window, smaller circular avatars, layout anchors. |
| **PR / Git** | Guidance on pushing branches, creating in-fork PRs, resolving divergent-branch pull and merge conflicts; JUnit dependency fix for tests. |
| **Misc** | Window title change; image swap (Snoopy/Wacka, Charlie Brown/user). |

## What worked well

- Refactoring and extracting methods from long blocks was straightforward; AI suggested clear method names and structure.
- Fixing compile errors (e.g. missing `LocalDate` import, `Wacka.RecurrenceType`) was quick.
- GUI tweaks (CSS, layout, error styling) were implemented in one pass with minimal back-and-forth.

## What didn’t or was tricky

- Merge conflict resolution required care so the refactored `Parser` wasn’t reverted by the assertion branch.
- Recurring-task behaviour (replace vs add on mark) needed a second pass to match the desired “replace with next occurrence” semantics.

## Time saved

Roughly **several hours** saved on refactoring, recurring-task design/implementation, and GUI styling compared to doing it manually. Assertions and small fixes would have been quick either way; the main gain was on recurring tasks and GUI layout/CSS.

---

*Last updated: Feb 2026*
