# Notator
musicXML File Writer for SuperCollider with the rewrite feature of the given sclang code to play it via scserver


## Description

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

## Examples

### Eighth-tone Scale through algorithmic construction
| Code | Results |
|------|---------|
|<pre>(<br>var title = "Eighth-tone Scale", score;<br>score = [<br>    (title: title, composer: 'a composer', right: '©'),<br>    (<br>        bar: 1, p1: (<br>            lbl: \Violin,<br>            atr: (key: [0, \none], time: [4, 4], staves: 1, clef: [[\g, 2]]),<br>            v1: []<br>        )<br>    )<br>];<br><br>(0, 1/4 .. 12).mirror.clump(4).do { arg notesInBar, index;<br>    var bar = index + 1;<br>    if (bar > 1) {<br>        score = score.add((bar: bar, p1: (v1: [])))<br>    };<br>    notesInBar.collect { arg aNote;<br>        score[bar][\p1][\v1] = score[bar][\p1][\v1].add([aNote + 60]) }<br>};<br><br>x = Notator.notate(<br>    score,<br>    ("~/Downloads/" ++ title ++ ".musicXML").standardizePath,<br>    'MuseScore 4'<br>)<br>)</pre>|Score in MuseScore 4:<br />![Eighth-tone_Scale-1](https://github.com/prko/Notator/assets/416281/b2928c56-a7a7-4ea5-b9c2-d6d3e5dbbd65)<br /><br />SCD file: [HelpSource/Classes/resources/Eighth-tone_Scale.scd](https://github.com/prko/Notator/blob/7dcb8e219d1079558eb7c55f62d1612701cf1952/HelpSource/Classes/resources/Eighth-tone_Scale.scd)|

### Twelve-tone matrix through algorithmic construction
| Code | Results |
|------|---------|
|<pre>(<br>var title = "twelve-tone series", matrix_12tone;<br>matrix_12tone = { arg array;<br>    var matrix = 12.collect { arg i; array[i]; array - (array[i] % 12) };<br>    matrix.do { arg item; item.replace(11, \e).replace(10, \t).postln }<br>};<br>matrix_12tone = matrix_12tone.((0..11).scramble) + 72;<br>    <br>x = (<br>    [(title: '12-tone series matrix', composer: 'randomised', rights: '©')] ++<br>    matrix_12tone.collect { arg series, index; <br>        if (index == 0) {<br>            (<br>                bar: index + 1,<br>                p1: (<br>                    lbl: '',<br>                    atr: (key: [0, \none], time: \x, staves: 1, clef: [[\g, 2]]),<br>                    v1: series.collect { arg aNote; [aNote, \w] }<br>                )<br>            )<br>        } {<br>            series.postln;<br>            (<br>                bar: index + 1,<br>                p1: (v1: series.collect { arg aNote; [aNote, \w] })<br>            )<br>        }<br>    }<br>).notate(("~/Downloads/" ++ title ++ ".musicXML").standardizePath, 'MuseScore 4')<br>)</pre>|Score in MuseScore 4:<br />![twelve-tone_seriesnotation](https://github.com/prko/Notator/assets/416281/231108b3-63dd-4884-9e04-8241a05003d9)<br /><br />SCD file: [HelpSource/Classes/resources/twelve-tone_series.scd](https://github.com/prko/Notator/blob/7dcb8e219d1079558eb7c55f62d1612701cf1952/HelpSource/Classes/resources/twelve-tone_series.scd)|
