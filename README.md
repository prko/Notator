> [WARNING]
> This quark is still under test and is not listed in the Community Quark Directory for SuperColllider.
> The feature, help documents and manual may be modified without a change log until it is listed in the Community Quark Directory.

musicXML File Writer for SuperCollider with its own music notation, similar to LilyPond. When writing musicXML from the input music notation, it also writes an SCD file by converting the input into a standard SuperCollider code block, so that you can play it back via scserver.

The included Pitch Class Set and Scientific Pitch Notation classes allow you to get MIDI pitch numbers (MIDI notes) or frequencies from pitch names and vice versa.

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fprko%2FNotator&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

---

### Table of Contents
<small><i>(<a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a>)</i></small>
- [1. How to Install](#1-how-to-install)
- [2. Classes](#2-classes)
  * [2.1. Notator](#21-notator)
    + [2.1.1. Examples](#211-examples)
      - [2.1.1.1. Eighth-tone Scale through algorithmic construction](#2111-eighth-tone-scale-through-algorithmic-construction)
      - [2.1.1.2. Twelve-tone matrix through algorithmic construction](#2112-twelve-tone-matrix-through-algorithmic-construction)
    + [2.1.2. Preview of Online Help Document and Guideline](#212-preview-of-online-help-document-and-guideline)
  * [2.2. PitchClassSet](#22-pitchclassset)
    + [2.2.1. Examples](#221-examples)
    + [2.2.2. Preview of Online Help Document](#222-preview-of-online-help-document)
  * [2.3. SPN](#23-spn)
    + [2.3.1. Examples](#231-examples)
    + [2.3.2. Preview of Online Help Document](#232-preview-of-online-help-document)

---

### 1. How to Install

To install Notator Library, copy and paste the following code into the SC-IDE editor window, and evaluate it:
```
(
"https://github.com/telephon/Strang".include;
"https://github.com/prko/Notator".include
)
```

---

### 2. Classes

#### 2.1. Notator
Using the Notator class, you can play the score via the sc-server and create musicXML files to seamlessly edit the score created in sclang in your favourite music notation software that supports musicXML 4.0 (or higher) import. It provides the following automated functions:

1. export a musicXML file and automatically open it in a music notation program,
2. create an SCD file and automatically load and open it to play music:
   - all standard and abbreviated notation is rewritten;
   - adds a new code block to play the transcribed code;
   - tied notes and articulation are applied for playback;
   - tries to associate the staff label with the same name as SynthDef. Synth(\default) will be used if the same defname is not found with the staff label.

You can print (or make a PDF of) the musicXML file without editing it, but you should edit and modify the score, since the Notator class is not intended for engraving music, but for composing seamlessly from music designed with SuperCollider.

> [NOTE]
The musical notation for the Notator class can be opened as a form of score in any music software program that supports musicXML 4.0 (or higher) import and a score view functionality. The scores used in the examples in this help document are encoded by Dorico 4 Pro for some examples that do not display the musicXML file correctly, but are otherwise encoded by MuseScore 4.

> [WARNING]
The way musicXML is decoded varies from software to software, so a musicXML file may look different in different software that opens it. Also, if the decoding is not correct, the score may look incorrect. You should therefore be familiar with the musicXML import characteristics of the software you use.

##### 2.1.1. Examples

###### 2.1.1.1. Eighth-tone Scale through algorithmic construction
| Code | Results |
|------|---------|
|<pre>(<br>var title = "Eighth-tone Scale", score;<br>score = [<br>    (title: title, composer: 'a composer', right: '©'),<br>    (<br>        bar: 1, p1: (<br>            lbl: \Violin,<br>            atr: (key: [0, \none], time: [4, 4], staves: 1, clef: [[\g, 2]]),<br>            v1: []<br>        )<br>    )<br>];<br><br>(0, 1/4 .. 12).mirror.clump(4).do { arg notesInBar, index;<br>    var bar = index + 1;<br>    if (bar > 1) {<br>        score = score.add((bar: bar, p1: (v1: [])))<br>    };<br>    notesInBar.collect { arg aNote;<br>        score[bar][\p1][\v1] = score[bar][\p1][\v1].add([aNote + 60]) }<br>};<br><br>x = Notator.notate(<br>    score,<br>    ("~/Downloads/" ++ title ++ ".musicXML").standardizePath,<br>    'MuseScore 4'<br>)<br>)</pre>|Score in MuseScore 4:<br />![Eighth-tone_Scale-1](https://github.com/prko/Notator/assets/416281/b2928c56-a7a7-4ea5-b9c2-d6d3e5dbbd65)[HelpSource/Classes/resources/Eighth-tone_Scale.musicXML](https://github.com/prko/Notator/blob/9f2e211d292d38cf93b99cbcee6af2f0902cba5a/HelpSource/Classes/resources/Eighth-tone_Scale.musicXML)<br /><br />SCD file: [HelpSource/Classes/resources/Eighth-tone_Scale.scd](https://github.com/prko/Notator/blob/9f2e211d292d38cf93b99cbcee6af2f0902cba5a/HelpSource/Classes/resources/Eighth-tone_Scale.scd)|

###### 2.1.1.2. Twelve-tone matrix through algorithmic construction
| Code | Results |
|------|---------|
|<pre>(<br>var title = "twelve-tone series", matrix_12tone;<br>matrix_12tone = { arg array;<br>    var matrix = 12.collect { arg i; array[i]; array - (array[i] % 12) };<br>    matrix.do { arg item; item.replace(11, \e).replace(10, \t).postln }<br>};<br>matrix_12tone = matrix_12tone.((0..11).scramble) + 72;<br>    <br>x = (<br>    [(title: '12-tone series matrix', composer: 'randomised', rights: '©')] ++<br>    matrix_12tone.collect { arg series, index; <br>        if (index == 0) {<br>            (<br>                bar: index + 1,<br>                p1: (<br>                    lbl: '',<br>                    atr: (key: [0, \none], time: \x, staves: 1, clef: [[\g, 2]]),<br>                    v1: series.collect { arg aNote; [aNote, \w] }<br>                )<br>            )<br>        } {<br>            series.postln;<br>            (<br>                bar: index + 1,<br>                p1: (v1: series.collect { arg aNote; [aNote, \w] })<br>            )<br>        }<br>    }<br>).notate(("~/Downloads/" ++ title ++ ".musicXML").standardizePath, 'MuseScore 4')<br>)</pre>|Score in MuseScore 4:<br />![twelve-tone_seriesnotation](https://github.com/prko/Notator/assets/416281/c87aeed7-bd9d-46b2-9641-0d37f7cd583d)[HelpSource/Classes/resources/twelve-tone_series.musicXML](https://github.com/prko/Notator/blob/9f2e211d292d38cf93b99cbcee6af2f0902cba5a/HelpSource/Classes/resources/twelve-tone_series.musicXML)<br /><br />SCD file: [HelpSource/Classes/resources/twelve-tone_series.scd](https://github.com/prko/Notator/blob/9f2e211d292d38cf93b99cbcee6af2f0902cba5a/HelpSource/Classes/resources/twelve-tone_series.scd)|

##### 2.1.2. Preview of Online Help Document and Guideline
- [Notator class](https://rawcdn.githack.com/prko/Notator/15d08873184c9ad81d8e558ca98875d5cc368de8/_Help%20(rendered%20HTML)/Classes/Notator.html)
  (Note: hyperlinks in the preview do not work!)
- [Score Guideline (needs further work)](https://rawcdn.githack.com/prko/Notator/e0bb6521e45af5d41259e67bcdf169f32f439b17/_Help%20(rendered%20HTML)/Reference/ScoreGuideline.html)
  (Note: hyperlinks in the preview do not work!)

#### 2.2. PitchClassSet

This is a Pitch Class Set implementation. Quarter tones are supported for the name system used in LilyPond and the musician-friendly naming convention. The sixteenth tones are supported for its own style \ez.

##### 2.2.1. Examples
```
\a.pitchClassNum // -> 9.0
\a.pcnum // -> 9.0

9.pitchClassName // -> [ a, gx, bff, a♮, g𝄪, b𝄫, an, a, gS, bF ]
9.pcname // -> [ a, gx, bff, a♮, g𝄪, b𝄫, an, a, gS, bF ]
```

##### 2.2.2. Preview of Online Help Document
- [PitchClassSet class](https://rawcdn.githack.com/prko/Notator/15d08873184c9ad81d8e558ca98875d5cc368de8/_Help%20(rendered%20HTML)/Classes/PitchClassSet.html)
  (Note: hyperlinks in the preview do not work!)

#### 2.3. SPN
Scientific Pitch Notation

This is an implementation of Scientific Pitch Notation (SPN). For the name system used in LilyPond (N.B.: the octave number follows the SPN.) and the musician-friendly naming convention, quarter tones are supported. For its own style \ez, sixteenth tones are supported.

In scientific pitch notation, middle A at 440 Hz is defined as A4. It is identical to the middle A at 440 Hz of A4 on Roland instruments, but 1 octave higher than the middle A at 440 Hz of A3 on Yahaha instruments. A4 in scientific notation is identical to A1 or A' in Helmholtz Pitch Notation.

##### 2.3.1. Examples
```
\a4.midi // -> 69.0
\a4.cps // -> 440.0

69.midispn // -> [ a4, gx4, bff4 ]
440.cpsspn // -> [ a4, gx4, bff4 ]
```

##### 2.3.2. Preview of Online Help Document
- [SPN class](https://rawcdn.githack.com/prko/Notator/15d08873184c9ad81d8e558ca98875d5cc368de8/_Help%20(rendered%20HTML)/Classes/SPN.html)
  (Note: hyperlinks in the preview do not work!)
