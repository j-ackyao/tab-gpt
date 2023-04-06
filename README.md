# Guitarist's Personal Tabs (GPT)

### What is this application?
GPT is a simple application that allows guitar
(or any other stringed instruments) players to write and store
their own personal guitar tabs without having to spend monthly
subscriptions or deal with unhandy programs.

This program will use tablature (tab) notation, where the user
will input the finger position of strings at a given beat.

> #### Example of Tab Notation
> 
> The following tab shows an E chord followed by a G13b9 chord
> ```
> -0------4-
> -0------5-
> -1------4-
> -2------3-
> -2--------
> -0------3-
> ```

The user of this program will be able to construct their own tabs
and save them on their local device.

### Who will use this application?

Me.

And other casual or amateur guitarists that want to write their own tabs
without learning other fancy professional programs. Or perhaps a
professional who only needs a simple tab to keep track of their
notes in a digital format without all the formal notations.

### My interest in this project
I ***despise*** working with guitar tab editing programs. Sometimes,
I don't care about the rhythm or the time signature. Sometimes,
I don't want to indicate every moment where there's a break and
how long it takes. Sometimes, I just want to write a digital tab
without having to dealing with all that fuss or to resort to using
a text editor.

I often transcribe songs that don't already have transcriptions online,
but I tend to forget my transcription the next day. I don't want to
spend an entire afternoon just to write out my tab in despised
programs, when that time could have just been spent relearning the part.

Many guitar tab writing programs are overly complicated for my
simple task, and I want a program that is:
- simple
- minimalistic
- quick




## User Stories
- [x] I want to be able to add chords to my tab
- [x] I want to be able to insert chords between chords in my tab
- [x] I want to be able to have and show custom tuning in my tab
- [x] I want to be able to copy and paste notes/chords in my tab
- [x] I want to be able to add slides/bends to my notes
- [x] I want to be able to save my tab
- [x] I want to be able to load my saved tab
- [x] I want a UI that's not just the console
- [x] I want to be able to export my tab into text

## Instructions for Grader

- #### First adding multiple Xs to a Y
  1. Have tab editing menu open (either new state or loaded state).
  2. `Add new chord` can be found on the top menu bar.
- #### Second adding multiple Xs to a Y
  1. Have tab editing menu open with an existing chord (can be added with previous action).
  2. Right-click existing chord to prompt popup menu.
  3. Generate second action by `Insert` or `Paste` (`Copy` must be used prior to `Paste`).
- #### Visual component
  - Found in main menu when launching program.
- #### Saving states
  1. Be in tab editing menu (either new state or loaded state).
  2. Save state by clicking `Save` found under `File` on the top left of the menu bar.
- #### Loading states
  1. Be in main menu (menu when program first loaded).
  2. Load state by clicking `Load tab` and providing existing tab name found in `./data/`.


### Phase 4: Task 2
```
Thu Apr 06 13:09:38 PDT 2023
Added chord to end of tab
Thu Apr 06 13:09:44 PDT 2023
Inserted [, , , , , ] at position 0
Thu Apr 06 13:10:14 PDT 2023
Inserted [, 3, 3, 3, x, 3] at position 1```