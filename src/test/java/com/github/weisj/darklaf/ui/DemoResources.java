/*
 * MIT License
 *
 * Copyright (c) 2021 Jannis Weis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.weisj.darklaf.ui;

import com.github.weisj.darklaf.util.DarkUIUtil;

import javax.swing.*;

public final class DemoResources
{
	public static final String LOREM_IPSUM =
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempor quis nibh a semper. Nullam\n"
					+" auctor, erat non viverra commodo, libero orci aliquam quam, ac interdum nunc est sed\n "
					+"ligula. Aliquam vel velit non dolor accumsan blandit id eu metus. Aenean iaculis urna in\n "
					+"placerat aliquam. Aliquam dui quam, bibendum sed magna in, cursus ornare est. Quisque\n "
					+"tempor nunc quis nunc tempor convallis. Vestibulum tristique luctus ante, ac hendrerit dui.\n\n"
					+"Donec ut maximus augue. Nam eleifend maximus scelerisque. Duis varius accumsan est, non\n "
					+"aliquam dolor. Aenean iaculis nibh in aliquam viverra. Sed laoreet, urna ut facilisis\n "
					+"convallis, arcu turpis vestibulum augue, id convallis tellus metus nec orci. Lorem ipsum\n "
					+"dolor sit amet, consectetur adipiscing elit. Donec hendrerit purus velit, at blandit elit\n "
					+"luctus ut. Proin diam nisl, sodales vitae dignissim nec, eleifend eu libero. Maecenas odio\n"
					+" ligula, fermentum eget nisl vel, cursus tristique est. In nec nibh nec dui tempor\n "
					+"ullamcorper. Praesent tincidunt luctus sem, ut luctus dolor commodo non. Nulla consectetur\n"
					+" facilisis dolor, in facilisis ligula fringilla et. Cras id placerat libero. Donec\n "
					+"vehicula orci a quam rutrum, eu efficitur lorem iaculis. Aenean varius nisi in dictum\n "
					+"accumsan.\n\n"
					+"Nulla massa ipsum, consectetur non gravida ut, blandit quis velit. Ut pretium quam aliquam\n"
					+" diam porttitor mattis. Nam ullamcorper, felis ut iaculis iaculis, nunc odio pulvinar \n"
					+"enim, vitae iaculis turpis sapien iaculis metus. Donec rutrum varius augue in dictum. Cras\n"
					+" vestibulum vitae mauris ut finibus. Ut dictum imperdiet lorem et imperdiet. Vivamus \n"
					+"semper tempor dolor eu porta. Sed at vehicula nisl. Pellentesque ut lorem tincidunt, \n"
					+"elementum ligula at, porta turpis. Praesent feugiat dolor diam, at facilisis metus gravida\n"
					+" non. Aliquam quis pellentesque nibh. Sed vestibulum porttitor nisi. In vitae malesuada\n "
					+"sapien.";
	public static final String HTML_DEMO = "<h1>Lorem ipsum dolor sit amet consectetuer adipiscing \n"+"elit</h1>\n"
			+"\n"+"\n"+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa \n"
			+"<strong>strong</strong>. Cum sociis natoque penatibus \n"
			+"et magnis dis parturient montes, nascetur ridiculus \n"
			+"mus. Donec quam felis, ultricies nec, pellentesque \n"
			+"eu, pretium quis, sem. Nulla consequat massa quis \n"
			+"enim. Donec pede justo, fringilla vel, aliquet nec, \n"
			+"vulputate eget, arcu. In enim justo, rhoncus ut, \n"
			+"imperdiet a, venenatis vitae, justo. Nullam dictum \n"
			+"felis eu pede <a class=\"external ext\" href=\"#\">link</a> \n"
			+"mollis pretium. Integer tincidunt. Cras dapibus. \n"
			+"Vivamus elementum semper nisi. Aenean vulputate \n"
			+"eleifend tellus. Aenean leo ligula, porttitor eu, \n"
			+"consequat vitae, eleifend ac, enim. Aliquam lorem ante, \n"
			+"dapibus in, viverra quis, feugiat a, tellus. Phasellus \n"
			+"viverra nulla ut metus varius laoreet. Quisque rutrum. \n"
			+"Aenean imperdiet. Etiam ultricies nisi vel augue. \n"+"Curabitur ullamcorper ultricies nisi.</p>\n"
			+"\n"+"\n"+"<h1>Lorem ipsum dolor sit amet consectetuer adipiscing \n"+"elit</h1>\n"+"\n"+"\n"
			+"<h2>Aenean commodo ligula eget dolor aenean massa</h2>\n"+"\n"+"\n"
			+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa. \n"
			+"Cum sociis natoque penatibus et magnis dis parturient \n"
			+"montes, nascetur ridiculus mus. Donec quam felis, \n"
			+"ultricies nec, pellentesque eu, pretium quis, sem.</p>\n"+"\n"+"\n"
			+"<h2>Aenean commodo ligula eget dolor aenean massa</h2>\n"+"\n"+"\n"
			+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa. \n"
			+"Cum sociis natoque penatibus et magnis dis parturient \n"
			+"montes, nascetur ridiculus mus. Donec quam felis, \n"
			+"ultricies nec, pellentesque eu, pretium quis, sem.</p>\n"+"\n"+"\n"+"<ul>\n"
			+"  <li>Lorem ipsum dolor sit amet consectetuer.</li>\n"+"  <li>Aenean commodo ligula eget dolor.</li>\n"
			+"  <li>Aenean massa cum sociis natoque penatibus.</li>\n"+"</ul>\n"+"\n"+"hr:"+"<hr></hr>"+"\n"
			+"custom div:"+"<div style=\"height: 0px; border-top-style: solid; border-width: 1px\"></div>"+"\n"
			+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa. \n"
			+"Cum sociis natoque penatibus et magnis dis parturient \n"
			+"montes, nascetur ridiculus mus. Donec quam felis, \n"
			+"ultricies nec, pellentesque eu, pretium quis, sem.</p>\n"+"\n"+"\n"
			+"<form action=\"#\" method=\"post\">\n"+"  <fieldset>\n"+"    <label for=\"name\">Name:</label>\n"
			+"    <input type=\"text\" id=\"name\" placeholder=\"Enter your \n"+"full name\" />\n"+"\n"
			+"    <label for=\"email\">Email:</label>\n"
			+"    <input type=\"email\" id=\"email\" placeholder=\"Enter \n"+"your email address\" />\n"+"\n"
			+"    <label for=\"message\">Message:</label>\n"
			+"    <textarea id=\"message\" placeholder=\"What's on your \n"+"mind?\"></textarea>\n"+"\n"
			+"    <input type=\"submit\" value=\"Send message\" />\n"+"\n"+"  </fieldset>\n"+"</form>\n"+"\n"
			+"\n"+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa. \n"
			+"Cum sociis natoque penatibus et magnis dis parturient \n"
			+"montes, nascetur ridiculus mus. Donec quam felis, \n"
			+"ultricies nec, pellentesque eu, pretium quis, sem.</p>\n"+"\n"+"\n"+"<table class=\"data\">\n"
			+"  <tr>\n"+"    <th>Entry Header 1</th>\n"+"    <th>Entry Header 2</th>\n"
			+"    <th>Entry Header 3</th>\n"+"    <th>Entry Header 4</th>\n"+"  </tr>\n"+"  <tr>\n"
			+"    <td>Entry First Line 1</td>\n"+"    <td>Entry First Line 2</td>\n"
			+"    <td>Entry First Line 3</td>\n"+"    <td>Entry First Line 4</td>\n"+"  </tr>\n"+"  <tr>\n"
			+"    <td>Entry Line 1</td>\n"+"    <td>Entry Line 2</td>\n"+"    <td>Entry Line 3</td>\n"
			+"    <td>Entry Line 4</td>\n"+"  </tr>\n"+"  <tr>\n"+"    <td>Entry Last Line 1</td>\n"
			+"    <td>Entry Last Line 2</td>\n"+"    <td>Entry Last Line 3</td>\n"
			+"    <td>Entry Last Line 4</td>\n"+"  </tr>\n"+"</table>\n"+"\n"+"\n"
			+"<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n"
			+"elit. Aenean commodo ligula eget dolor. Aenean massa. \n"
			+"Cum sociis natoque penatibus et magnis dis parturient \n"
			+"montes, nascetur ridiculus mus. Donec quam felis, \n"
			+"ultricies nec, pellentesque eu, pretium quis, sem.</p>\n";
	public static final String KERNING_TEST = "Basic Kerning Text\n"+"\n"+"\n"+"BASIC ALPHABET \n"+"\n"
			+"AÆBCÇDEFGH\n"+"IJKLMNOŒØP\n"+"QRSTUVWXYZ/\\\n"+"aæbcçdefﬁﬂghijklm\n"+"noœøpqrstuvwxyz\n"
			+"1234567890\n"+"°’”$¢ƒß£¥%⁄#\n"+"&.,:;…·!?“”‘’\n"+"•- – —*„‚_|®©™\n"+"([{«»‹›}])\n"
			+"ˆ˜¯˘˙˚¸˝˛ˇ`´¨\n"+"†‡§¶@+=±\n"+"\n"+"\n"+"FOREIGN CHARS\n"+"\n"+"ÄÅÂÁÀÃÇÉÊËÈÍÎÏÌ \n"
			+"ÑÖÓÔÒÕÜÚÛÙŸ\n"+"áàâäãåçéèêëíìîïñ\n"+"óòôöõúùûüÿ\n"+"\n"+"\n"+"Pi & OTHER/TEXT FONTS\n"+"\n"
			+"_^|∏πΩ¬ªº¿¡\n"+"∆◊§∞µ∂∑€‰\n"+"\n"+"\n"+"SAMPLE KERNING PAIRS\n"+"\n"
			+"AT AV AW AY Av Aw Ay \n"+"Fa Fe Fo Kv Kw Ky LO \n"+"LV LY PA Pa Pe Po TA \n"
			+"Ta Te Ti To Tr Ts Tu Ty \n"+"UA VA Va Ve Vo Vr Vu Vy \n"+"WA WO Wa We Wr Wv Wy \n"+"\n"+"\n"
			+"SAMPLE LOWERCASE\n"+"\n"+"INCIDENTALS\n"+"\n"+"w! w? f! f? ¡a ¿a\n"+"«n»«o»‹n›‹o›\n"
			+"h∂ho&o‚{h}h•h\n"+"o•oh@hªhº\n"+"h⁄h‚h„h™h®h©h\n"+"h·h o·o`´˘¨ˆ˜¯ˇ˙˚¸˝˛ˆ\n"+"h”h“h“w” \n"+"\n"
			+"h.h,h:h;‘h’\n"+"o.o,o:o;‘o’\n"+"(h)[h]h/h\\h\n"+"(o)[o]h-ho-o\n"+"(h)[h]h/h\\h#\n"
			+"(o)[o]h-ho-h\n"+"h£h$h¢hƒh¥h\n"+"h*h†h‡h§\n"+"h–ho–o\n"+"h—ho—o\n"+"h+h=h±h'h\"h°h#h\n"
			+"h%h‰h…h\n"+"h1h2h3h4h5h0h\n"+"h6h7h8h9h0h\n"+"£110121314151\n"+"$16171819122\n"+"23242526¢\n"
			+"27282930%\n"+"\n"+"\n"+"SAMPLE SIDEBARING\n"+"\n"+"HAHBHCHDHEHFHGHHH\n"
			+"HIHJHKHLHMHNHOHPHQH\n"+"HRHSHTHUHVHWHXHYHZH\n"+"HÆHŒHÄHÖHÜHÅHÇHÁH\n"+"HÛHÍHÏHÌHÓEHÙHØH&HaH\n"
			+"HbHcHdHeHfHgHhHiHjHvkH\n"+"HlHmHnHoHpHqHrHsHtHuH\n"+"HvHwHxHyHzHßHæHœHäH\n"
			+"HöHüHﬁHﬂHåHıHçHøH1H2H\n"+"H3H4H5H6H7H8H9H0H£H¢H\n"+"H$H¥HƒH!H¡H?H¿H*H#H/H+H\n"
			+"H=H÷H≠H±HvH∂H◊H≈H√H~HµH\n"+"H∞H.H,H;H:H”H”H”H‘H’H…H\n"+"H‹H›H»H«H[H]H(H)H{H}H⁄H–H\n"
			+"H‰H%H¶H|H’H™H®H©H•H@H\n"+"H˘HˆH`H´HˇH˜H¨H·H¸H¯H˚H˛H˝H˙H\n"+"\n"+"\n"+"SAMPLE TEXT\n"+"\n"
			+"Think about it, your unique designs \n"+"evaluated by experts in typography, \n"
			+"fabulous prizes, recognition and the \n"+"envy of your peers! Not only that, your \n"
			+"work will be offered for sale around \n"+"the world by the best alternative type \n"
			+"foundry going. And that would be \n"+"us...GarageFonts.\n"+"\n"+"\n"+"COMPARISONS\n"+"\n"
			+"÷±≤≥–—≠<=>-+≈\n"+"([{«»‹›}])‰%¡¿!?\n"+"“”‘’°’”ˆ˜¯˘˙˚˝ˇ`´¨„‚.,:;…·\n"+"®©™@\n"+"\n"+"\n"
			+"ALL CAPS\n"+"\n"+"AABACADAEAFAGAHAIAJAK\n"+"ALAMANAOAPAQARASATAU\n"+"AVAWAXAYAZA A’S A”\n"
			+"A-A–A—A…A. A, A; A: A? A!\n"+"BABBCBDBEBFBGBHBIBJBK\n"+"BLBMBNBOBPBQBRBSBTBU\n"
			+"BVBWBXBYBZB B’S B”\n"+"B-B–B—B…B. B, B; B: B? B!\n"+"CACBCCDCECFCGCHCICJCK\n"
			+"CLCMCNCOCPCQCRCSCTCU\n"+"CVCWCXCYCZC C’S C”\n"+"C-C–C—C…C. C, C; C: C? C!\n"
			+"DADBDCDDEDFDGDHDIDJDK\n"+"DLDMDNDODPDQDRDSDTDU\n"+"DVDWDXDYDZD D’S D”DØD\n"
			+"D-D–D—D…D. D, D; D: D? D!\n"+"EAEBECEDEEFEGEHEIEJEK\n"+"ELEMENEOEPEQERESETEU\n"
			+"EVEWEXEYEZE E’S E”\n"+"E-E–E—E…E. E, E; E: E? E!\n"+"FAFBFCFDFEFFGFHFIFJFK\n"
			+"FLFMFNFOFPFQFRFSFTFU\n"+"FVFWFXFYFZF F’S F”\n"+"F-F–F—F…F. F, F; F: F? F!\n"
			+"GAGBGCGDGEGFGGHGIGJGK\n"+"GLGMGNGOGPGQGRGSGTGU\n"+"GVGWGXGYGZG G’S G”\n"
			+"G-G–G—G…G. G, G; G: G? G!\n"+"HAHBHCHDHEHFHGHHIHJHK\n"+"HLHMHNHOHPHQHRHSHTHU\n"
			+"HVHWHXHYHZH H’S H”\n"+"H-H–H—H…H. H, H; H: H? H!\n"+"IAIBICIDIEIFIGIHIIJIK\n"
			+"ILIMINIOIPIQIRISITIUIVIWIXIYIZ\n"+"I’S I”I-I–I—I…I. I, I; I: I? I!\n"+"JAJBJCJDJEJFJGJHJIJJK\n"
			+"JLJMJNJOJPJQJRJSJTJU\n"+"JVJWJXJYJZJ J’S J”\n"+"J-J–J—J…J. J, J; J: J? J!\n"
			+"KAKBKCKDKEKFKGKHKIKJK\n"+"KLKMKNKOKPKQKRKSKTKU\n"+"KVKWKXKYKZK K’S K”\n"
			+"K-K–K—K…K. K, K; K: K? K!\n"+"LALBLCLDLELFLGLHLILJLK\n"+"LLMLNLOLPLQLRLSLTLU\n"
			+"LVLWLXLYLZL L’S L”\n"+"L-L–L—L…L. L, L; L: L?L\n"+"L!MAMBMCMDMEMFMGMHMIMJMK\n"
			+"MLMMNMOMPMQMRMSMTMU\n"+"MVMWMXMYMZM M’S M”\n"+"M-M–M—M…M. M, M; M: M? M!\n"
			+"NANBNCNDNENFNGNHNINJNK\n"+"NLNMNNNONPNQNRNSNTNU\n"+"NVNWNXNYNZN N’S N”\n"
			+"N-N–N—N…N. N, N; N: N? N!\n"+"OAOBOCODOEOFOGOHOIOJOK\n"+"OLOMONOOPOQOROSOTOU\n"
			+"OVOWOXOYOZO O’S O”\n"+"O-O–O—O…O. O, O; O: O? O!\n"+"PAPBPCPDPEPFPGPHPIPJPK\n"
			+"PLPMPNPOPPQPRPSPTPU\n"+"PVPWPXPYPZP P’S P”\n"+"P-P–PP—P…P. P, P; P: P? P!\n"
			+"QAQBQCQDQEQFQGQHQIQJQK\n"+"QLQMQNQOQPQQRQSQTQU\n"+"QVQWQXQYQZQ Q’S Q”\n"
			+"Q-Q–Q—Q…Q. Q, Q; Q: Q? Q!\n"+"RARBRCRDRERFRGRHRIRJRK\n"+"RLRMRNRORPRQRRSRTRU\n"
			+"RVRWRXRYRZR R’S R”\n"+"R-R–R—R…R. R, R; R: R? R!\n"+"SASBSCSDSESFSGSHSISJSK\n"
			+"SLSMSNSOSPSQSRSSTSU\n"+"SVSWSXSYSZS S’S S”\n"+"S-S–S—S…S. S, S; S: S? S!\n"
			+"TATBTCTDTETFTGTHTITJTK\n"+"TLTMTNTOTPTQTRTSTTU\n"+"TVTWTXTYTZT T’S T”\n"
			+"T-T–T—T…T. T, T; T: T? T!\n"+"UAUBUCUDUEUFUGUHUIUJUK\n"+"ULUMUNUOUPUQURUSUTUU\n"
			+"UVUWUXUYUZU U’S U”\n"+"U-U–U—U…U. U, U; U: U? U!\n"+"VAVBVCVDVEVFVGVHVIVJVK\n"
			+"VLVMVNVOVPVQVRVSVTVU\n"+"VVWVXVYVZV V’S V”\n"+"V-V–V—V…V. V, V; V: V? V!\n"
			+"WAWBWCWDWEWFWGWHWIWJWK\n"+"WLWMWNWOWPWQWRWSWTWU\n"+"WVWWXWYWZW W’S W”\n"
			+"W-W–W—W…W. W, W; W: W? W!\n"+"XAXBXCXDXEXFXGXHXXIXJXK\n"+"XLXMXNXOXPXQXRXSXTXU\n"
			+"XVXWXXYXZX X’S X”\n"+"X-X–X—X…X. X, X; X: X? X!\n"+"YAYBYCYDYEYFYGYHYIYJYK\n"
			+"YLYMYNYOYPYQYRYSYTYU\n"+"YVYWYXYYZY Y’S Y”\n"+"Y-Y–Y—Y…Y. Y, Y; Y: Y? Y!\n"
			+"ZAZBZCZDZEZFZGZHZIZJZK\n"+"ZLZMZNZOZPZQZRZSZTZU\n"+"ZVZWZXZYZZ Z’S Z”\n"
			+"Z-Z–Z—Z…Z. Z, Z; Z: Z? Z!\n"+"\n"+"\n"+"LOWER CASE\n"+"\n"+"aabacadaeafagahaiajakalama\n"
			+"anaoapaqarasatauavawaxayaza\n"+"a…a. a, a; a: a! a? a-a–a—a’s a”\n"+"babbcbdbebfbgbhbibjbkblbmb\n"
			+"bnbobpbqbrbsbtbubvbwbxbybzb\n"+"b…b. b, b; b: b! b? b-b–b—b’s b”\n"+"cacbccdcecfcgchcicjckclcmc\n"
			+"cncocpcqcrcsctcucvcwcxcyczc\n"+"c…c. c, c; c: c! c? c-c–c—c’s c”\n"+"dadbdcdddedfdgdhdidjdkdldmd\n"
			+"dndodpdqdrdsdtdudvdwdxdydzd\n"+"d…d. d, d; d: d! d? d-d–d—d’s d”\n"+"eaebecedeeefegeheiejekeleme\n"
			+"eneoepeqereseteuevewexeyeze\n"+"e…e. e, e; e: e! e? e-e–e—e’s e”\n"+"fafbfcfdfefffgfhfifjfkflfmf\n"
			+"fnfofpfqfrfsftfufvfwfxfyfzf\n"+"f…f. f, f; f: f! f? f-f–f—f’s f”\n"+"gagbgcgdgegfggghgigjgkglgmg\n"
			+"gngogpgqgrgsgtgugvgwgxgygzg\n"+"g…g. g, g; g: g! g? g-g–g—g’s g”\n"+"hahbhchdhehfhghhhihjhkhlhmh\n"
			+"hnhohphqhrhshthuhvhwhxhyhzh\n"+"h…h. h, h; h: h! h? h-h–h—h’s h”\n"
			+"iaibicidieifigihiiijikiliminioipiqirisiti\n"+"iuiviwixiyizi i…i. i, i; i: i! i? i-i–i—i’s i”\n"
			+"jajbjcjdjejfjgjhjijjjkjljmjjnjojpjqjrjsjtjujvj\n"+"jwjxjyjzj j…j. j, j; j: j! j? j-j–j—j’s j”\n"
			+"kakbkckdkekkfkgkhkikjkkklkmknkok\n"+"kpkqkrksktkukkvkwkxkykzk\n"+"k…k. k, k; k: k! k? k-k–k—k’s k”\n"
			+"lalblcldlelflglhliljlklllmlnlolplqlrlsltlulvl\n"+"lwlxlylzl l…l. l, l; l: l! l? l-l–l—l’s l”\n"
			+"mambmcmdmemfmgmhmimjmkm\n"+"mlmmmnmompmqmrmsmtmumvm\n"+"mwmxmymzm’s m”\n"
			+"m…m. m, m; m: m! m? m-m–m—m\n"+"nanbncndnenfngnhninjnknlnmn\n"+"nnnonpnqnrnsntnunvnwnxnynzn\n"
			+"n…n. n, n; n: n! n? n-n–n—n’s n”\n"+"oaobocodoeofogohoiojokolomo\n"+"onooopoqorosotouovowoxoyozo\n"
			+"o…o. o, o; o: o! o? o-o–o—o’s o”\n"+"papbpcpdpepfpgphpipjpkplpmp\n"+"pnpopppqprpsptpupvpwpxpypzp\n"
			+"p…p. p, p; p: p! p? p-p–p—p’s p”\n"+"qaqbqcqdqeqfqgqhqiqjqkqlqmq\n"+"qnqoqpqqqrqsqtquqvqwqxqyqzq\n"
			+"q…q. q, q; q: q! q? q-q–q—q’s q”\n"+"rarbrcrdrerfrgrhrirjrkrlrmr\n"+"rnrorprqrrrsrtrurvrwrxryrzr\n"
			+"r…r. r, r; r: r! r? r-r–r—r’s r”\n"+"sasbscsdsesfsgshsisjskslsms\n"+"snsospsqsrssstsusvswsxsyszs\n"
			+"s…s. s, s; s: s! s? s-s–s—s’s s”\n"+"tatbtctdtetftgthtitjtktltmt\n"+"tntotptqtrtstttutvtwtxtytzt\n"
			+"t…t. t, t; t: t! t? t-t–t—t’s t”\n"+"uaubucudueufuguhuiujukulumu\n"+"unuoupuqurusutuuuvuwuxuyuzu\n"
			+"u…u. u, u; u: u! u? u-u–u—u’s u”\n"+"vavbvcvdvevfvgvhvivjvkvlvmv\n"+"vnvovpvqvrvsvtvuvvvwvxvyvzv\n"
			+"v…v. v, v; v: v! v? v-v–v—v’s v”\n"+"wawbwcwdwewfwgwhwiwjwkw\n"+"wlwmwnwowpwqwrwswtwuwvw\n"
			+"wwxwywzw’s w”\n"+"w…w. w, w; w: w! w? w-w–w—w\n"+"xaxbxcxdxexfxgxhxixjxkxlxmxnx\n"
			+"xoxpxqxrxsxtxuxvxwxxxyxzx\n"+"x…x. x, x; x: x! x? x-x–x—x’s x”\n"+"yaybycydyeyfygyhyiyjykylymyny\n"
			+"yoypyqyrysytyuyvywyxyyyzy\n"+"y…y. y, y; y: y! y? y-y–y—y’s y”\n"+"zazbzczdzezfzgzhzizjzkzlzmznz\n"
			+"zozpzqzzrzsztzuzvzwzxzyzzz\n"+"z…z. z, z; z: z! z? z-z–z—z’s z”\n"+"\n"+"\n"+"UPPER & LOWER\n"
			+"\n"+"Aaron Able Ache Advert\n"+"Aegis Aft Age Ahe Ails \n"+"Ajar Akin Aloe Amish And\n"
			+"Aone Ape Aqua Are Ascot\n"+"Atlas Auto Avon Awe Axe \n"+"Aye Azo Aí Aì Aî Aï\n"
			+"Band Bet Bing Bloat Bog\n"+"Bring Bumpy Byte Bí Bì Bî Bï\n"+"Carry Celar Cinthia Cope Crap \n"
			+"Cult Cycle Cí Cì Cî Cï\n"+"Dark Demon Dingy Dope Dumb \n"+"Dí Dì Dî Dï\n"
			+"Each Eels Einar Eons Euchre \n"+"Ever Ewer Exit Eyes Eí Eì Eî Eï\n"
			+"Fact Fever Fire Fıne Font Framer \n"+"Fur Fyrd Fähr Földer Fà Få Fál \n"
			+"Fâl Fãl Fè Fé Fêl Fël Fíl Fìl Fîl Fïl \n"+"Fól Fò Fôl Fõl Fúl Fù Fûl Fÿ Fünk \n"
			+"Gayle Gentle Girl Gnome Gí Gì Gî Gï\n"+"Gonot Grinning Gulf Gwen Gyro \n"
			+"Harder Help Hilton Honor Hunk\n"+"Hv Hymn Hí Hì Hî Hï\n"+"Ian Ieo Iggy Iillian Ion Iugia Iyaaa\n"
			+"Ií Iì Iî Iï \n"+"Jacky Jester Jimmy Joint Junk  \n"+"Jí Jì Jî Jï\n"+"Kangaroo Keep Kill Kline\n"
			+"Kop Kudees Kwick Kva Kyle \n"+"Kärn Köff Küdos Kì Kî Kï Kÿ\n"+"Lay Learned Listing Load Lung\n"
			+"Lw Lynch \n"+"Mail Meal Minx Mode Music \n"+"Myth Mí Mì Mî Mï\n"+"Nail Next Nile Nooky Numb \n"
			+"Ní Nì Nî Nï\n"+"Oatmeal Oer Offer Ogor Oink Oolong \n"+"Out Over Oyster Oí Oì Oî Oï\n"
			+"Painter Peal Pile Pıne Pjb Plaster\n"+"Pointer Printer Putt Pygmy Päl Pöl\n"
			+"Pünk Pál Pà Pâl Pãl På Pél Pè \n"+"Pêl Píl Pì Pîl Pïl Pól Pò Pôl\n"+"Põl Púl Pù Pûl Pÿ\n"
			+"Qanat Qels Qix Qon Quest \n"+"Qí Qì Qî Qï\n"+"Rate Red Right Royal Run \n"+"Ryal Rän Röad Rüng\n"
			+"Rí Rì Rî Rï\n"+"Sallie Scuzz Sensation Shell Sink \n"+"Smelly Snowball Soul Spoke Sqish \n"
			+"Stoner Sung Svelt Swap Symantic\n"+"Sí Sì Sî Sï\n"+"Tail Teal Them Timer Tıme \n"
			+"Toll Trustee Tsing Tumbs Twizzlers\n"+"Typing Täp Törn Tüff Tál Tà Tâl \n"
			+"Tãl Tål Tél Tè Têl Tël Tíl Tì Tîl \n"+"Tïl Tól Tò Tôl Tõl Túl Tù Tûl Tÿ\n"
			+"Uarco Ue Ui Umbrella Under Uo \n"+"Upper Ursula User Utterly Uu Uv \n"+"Uwe Uí Uì Uî Uï\n"
			+"Vain Vc Veto Vine Vıne Vlad\n"+"Vow Vroor Vs Vulgar Vying \n"+"Vämping Vöter Vál Và Vâl Vãl Vå \n"
			+"Vél Vè Vêl Vë Víl Vìl Vîl Vïl Vól \n"+"Vò Vôl Vö Võl Vúl Vù Vûl Vü Vÿ\n"
			+"Wale Wet What Window Wınd \n"+"Wm Wow Wren Wsa Wte Wud \n"+"Wxe Wynde Wäne Wál Wà Wâl\n"
			+"Wãl Wå Wél Wè Wêl Wël Wíl \n"+"Wìl Wîl Wïl Wól Wò Wôl Wöl \n"+"Wÿ Wõl Wúl Wù Wûl Wül\n"
			+"Xanth Xelo Xi Xo Xu Xylo \n"+"Xál Xà Xâ Xä Xã Xå Xé Xè\n"+"Xê Xë Xíl Xìl Xîl Xïl Xó Xò Xô \n"
			+"Xö Xõ Xú Xù Xû Xü Xå Xÿ\n"+"Yale Yddes Yearling Yicks Yıeld \n"+"Yoyo Ypring Yrs Ys Yuck \n"
			+"Yviye Yz Yá Yà Yâ Yä Yã Yå \n"+"Yé Yè Yê Yë Yí Yì Yî Yï Yó \n"+"Yò Yô Yö Yõ Yú Yù Yû Yü  \n"
			+"Zanzabar Zellis Zion Zope Zulu \n"+"Zyle Zí Zì Zî Zï\n"+"\n"+"\n"+"SIDE BEARINGS\n"+"\n"
			+"HIHOH\n"+"HAHBHCHDHEH\n"+"OAOBOCODOEO\n"+"HFHGHHHIHJH\n"+"OFOGOHOIOJO\n"+"HKHLHMHNHOHPHON\n"
			+"OKOLOMONOOPO\n"+"HQUHRHSHTHUH\n"+"OQOROSOTOUO\n"+"HVHWHXHYHZH\n"+"OVOWOXOYOZO\n"
			+"HÌHÍHÏHÎHHÆHŒH\n"+"HØHHÇHOÇO\n"+"HIHOH  Hillolih\n"+"nanbncndnenfngno\n"+"oaobocodoeofogo\n"
			+"nhninjnknlnmnnn\n"+"ohoiojokolomono\n"+"nonpnqnrnsntnun\n"+"ooopoqorosotouo\n"+"nvnwnxnynzn\n"
			+"ovowoxoyozo\n"+"nßnænœnønnçn\n"+"oßoæoœoøooço\n"+"líll lìll lîll lïll lildibıb\n"
			+"√oﬁlloﬂilo≠o¶o∞H¬\n"+"IOIH! IOIH? f! f? ¡H! ¿H?\n"+"«n»«o»‹n›‹o›\n"+"H&HO&O{H}H\n"
			+"H•HO•OH@HªHº\n"+"H⁄H‚H„H™H®H©H\n"+"H·H O·O`´˘¨ˆ˜¯ˇ˙˚¸˝˛ˆ\n"+"“HIOH” \n"+"IOIH. H, H: H;\n"
			+"O.O,O:O;‘O’\n"+"‘HIH’S “HIH” ‘H’ \n"+"(H)[H]{H}(O)[O]\n"+"H/H\\H\n"+"H£0H$0H0¢Hƒ7H¥7H\n"
			+"H*H†H‡H§H\n"+"IOH-HO-O\n"+"H–HO–O\n"+"H—HO—O\n"+"1'1\"0°H#7#0H\n"+"00%00‰H…H\n"+"H=H+H±H\n"
			+"H0H1H2H3H4H5H\n"+"H6H7H8H9HOH\n"+"00110121314151\n"+"16171819122\n"+"202122324252\n"
			+"2627282930\n"+"\n"+"\n"+"CAPS & PUNCTUATION\n"+"\n"+"H…H. H: H, H; H! H?\n"
			+"A…A. A: A, A; A! A?\n"+"B…B. B: B, B; B! B?\n"+"C…C. C: C, C; C! C?\n"+"D…D. D: D, D; D! D?\n"
			+"E…E. E: E, E; E! E?\n"+"F…F. F: F, F; F! F?\n"+"G…G. G: G, G; G! G?\n"+"H…H. H: H, H; H! H?\n"
			+"I…I. I: I, I; I! I?\n"+"J…J. J: J, J; J! J?\n"+"K…K. K: K, K; K! K?\n"+"L…L. L: L, L; L! L?\n"
			+"M…M. M: M, M; M! M?\n"+"N…N. N: N, N; N! N?\n"+"O…O. O: O, O; O! O?\n"+"P…P. P: P, P; P! P?\n"
			+"Q…Q. Q: Q, Q; Q! Q?\n"+"R…R. R: R, R; R! R?\n"+"S…S. S: S, S; S! S?\n"+"T…T. T: T, T; T! T?\n"
			+"U…U. U: U, U; U! U?\n"+"V…V. V: V, V; V! V?\n"+"W…W. W: W, W; W! W?\n"+"X…X. X: X, X; X! X?\n"
			+"Y…Y. Y: Y, Y; Y! Y?\n"+"Z…Z. Z: Z, Z; Z! Z?\n"+"\n"+"H-H–H—H’S “H” ‘H’\n"+"A-A–A—AA’S “A”\n"
			+"B-B–B—BB’S “B”\n"+"C-C–C—CC’S “C”\n"+"D-D–D—DD’S “D”\n"+"E-E–E—EE’S “E”\n"+"F-F–F—FF’S “F”\n"
			+"G-G–G—GG’S “G”\n"+"H-H–H—HH’S “H”\n"+"I-I–I—II’S “I”\n"+"J-J–J—JJ’S “J”\n"+"K-K–K—KK’S “K”\n"
			+"L-L–L—LL’S “L”\n"+"M-M–M—MM’S “M”\n"+"N-N–N—NN’S “N”\n"+"O-O–O—OO’S “O”\n"+"P-P–P—PP’S “P”\n"
			+"Q-Q–Q—QQ’S “Q”\n"+"R-R–R—RR’S “R”\n"+"S-S–S—SS’S “S”\n"+"T-T–T—TT’S “T”\n"+"U-U–U—UU’S “U”\n"
			+"V-V–V—VV’S “V”\n"+"W-W–W—WW’S “W”\n"+"X-X–X—XX’S “X”\n"+"Y-Y–Y—YY’S “Y”\n"+"Z-Z–Z—ZZ’S “Z”\n"
			+"\n"+"\n"+"LOWER CASE & PUNCTUATION\n"+"\n"+"H…H. H! H?\n"+"l…l. l: l, l; l! l?\n"
			+"a…a. a: a, a; a! a?\n"+"b…b. b: b, b; b! b?\n"+"c…c. c: c, c; c! c?\n"+"d…d. d: d, d; d! d?\n"
			+"e…e. e: e, e; e! e?\n"+"f…f. f: f, f; f! f?\n"+"g…g. g: g, g; g! g?\n"+"h…h. h: h, h; h! h?\n"
			+"i…i. i: i, i; i! i?\n"+"j…j. j: j, j; j! j?\n"+"k…k. k: k, k; k! k?\n"+"l…l. l: l, l; l! l?\n"
			+"m…m. m: m, m; m! m?\n"+"n…n. n: n, n; n! n?\n"+"o…o. o: o, o; o! o?\n"+"p…p. p: p, p; p! p?\n"
			+"q…q. q: q, q; q! q?\n"+"r…r. r: r, r; r! r?\n"+"s…s. s: s, s; s! s?\n"+"t…t. t: t, t; t! t?\n"
			+"u…u. u: u, u; u! u?\n"+"v…v. v: v, v; v! v?\n"+"w…w. w: w, w; w! w?\n"+"x…x. x: x, x; x! x?\n"
			+"y…y. y: y, y; y! y?\n"+"z…z. z: z, z; z! z?\n"+"\n"+"H-H–H—H’S “H” ‘H’\n"+"l-l–l—ll’s “l” ‘l’\n"
			+"o-o–o—oso’s “o”\n"+"a-a–a—asa’s “a”\n"+"b-b–b—bsb’s “b”\n"+"c-c–c—csc’s “c”\n"
			+"d-d–d—dsd’s “d”\n"+"e-e–e—ese’s “e”\n"+"f-f–f—fsf’s “f” \n"+"g-g–g—gsg’s “g”\n"
			+"h-h–h—hsh’s “h”\n"+"i-i–i—isi’s “i”\n"+"j-j–j—jsj’s “j”\n"+"k-k–k—ksk’s “k”\n"
			+"l-l–l—lsl’s “l”\n"+"m-m–m—msm’s “m”\n"+"n-n–n—nsn’s “n”\n"+"o-o–o—oso’s “o”\n"
			+"p-p–p—psp’s “p”\n"+"q-q–q—qsq’s “q”\n"+"r-r–r—rsr’s “r”\n"+"s-s–s—sss’s “s”\n"
			+"t-t–t—tst’s “t”\n"+"u-u–u—usu’s “u”\n"+"v-v–v—vsv’s “v”\n"+"w-w–w—wsw’s “w”\n"
			+"x-x–x—xsx’s “x”\n"+"y-y–y—ysy’s “y”\n"+"z-z–z—zsz’s “z”\n"+"\n"+"\n"+"INCIDENTALS\n"+"\n"
			+"A* B* C* D* E* \n"+"F* G* H* I* J* K*\n"+"L* M* N* O* P* \n"+"Q* R* S* T* U* \n"
			+"V* W* X* Y* Z*\n"+"a* b* c* d* e* \n"+"f* g* h* i* j* k*\n"+"l* m* n* o* p* \n"
			+"q* r* s* t* u* \n"+"v* w* x* y* z*\n"+"\n"+"f.* f,* f;* f:*\n"+"r.* r,* r;* r:*\n"
			+"v.* v,* v;* v:*\n"+"w.* w,* w;* w:*\n"+"y.* y,* y;* y:*     \n"+"T.* T,* T;* T:*\n"
			+"V.* V,* V;* V:*\n"+"W.* W,* W;* W:*\n"+"Y.* Y,* Y;* Y:*\n"+"f.” f,” f.’ f,’\n"
			+"r.” r,” r.’ r,’ \n"+"v.” v,” v.’ v,’ \n"+"w.” w,” w.’ w,’ \n"+"y.” y,” y.’ y,’   \n"
			+"T.” T,” T.’ T,’  \n"+"V” V.” V,” V.’ V,’ \n"+"W.” W,” W.’ W,’ \n"+"Y.” Y,” Y.’ Y,’ \n"
			+"“…” ‘…’ “ ” \n"+"“HIH!” !’ \n"+"“HIH?” ?’ \n"+"WE’D I’LL \n"+"I’M TAM’O\n"+"WE’RE WE'VE\n"
			+"WHO’S WHAT'S\n"+"AIN’T I’VE\n"+"edWe’d I’d We’ll\n"+"I’m tam’omo \n"+"we’rer who’s \n"
			+"isn’t I’ve\n"+"‘A’ ‘E’ ‘J’ ‘O’ ‘T’ \n"+"‘V’ ‘W’ ‘Y’ \n"+"“A” “E” “J” \n"+"“O” “T”\n"
			+"“V” “W” “Y” \n"+"\n"+"/A/B/C/D/E/F/\n"+"/G/H/I/J/K/L/ \n"+"/M/N/O/P/Q/\n"+"/R/S/T/U/V/A \n"
			+"/W/X/Y/Z/   \n"+"/a/b/c/d/e/f/ \n"+"/g/h/i/j/k/l/m/\n"+"/n/o/p/q/r/s/ \n"+"/t/u/v/w/x/y/z/ \n"
			+"/0/1/2/3/4/\n"+"/5/6/7/8/9/\n"+" \n"+"A™ A® B™ B® C™ C® \n"+"D™ D® E™ E® F™ F®\n"
			+"G™ G® H™ H® I™ I®\n"+"J™ J® K™ K® L™ L® \n"+"M™ M® N™ N® O™ O®\n"+"P™ P® Q™ Q® R™ R®\n"
			+"S™ S® T™ T® U™ U® \n"+"V™ V® W™ W® X™ X®\n"+"Y™ Y® Z™ Z® \n"+"a™ a® b™ b® c™ c® \n"
			+"d™ d® e™ e® f™ f®\n"+"g™ g® h™ h® i™ i®\n"+"j™ j® k™ k® l™ l® \n"+"m™ m® n™ n® o™ o®\n"
			+"p™ p® q™ q® r™ r®\n"+"s™ s® t™ t® u™ u® \n"+"v™ v® w™ w® x™ x® \n"+"y™ y® z™ z®\n"+"\n"
			+"001020304050\n"+"0607080900\n"+"101121314151\n"+"16171819100\n"+"202122324252\n"
			+"26272829200\n"+"303132334353\n"+"36373839300\n"+"404142434454\n"+"46474849400\n"
			+"505152535455\n"+"56575859500\n"+"606162636465\n"+"6676869600\n"+"707172737475\n"+"7677879700\n"
			+"808182838485\n"+"8687889800\n"+"909192939495\n"+"9697989900\n"+"(1)(2)(3)(4)(5)\n"
			+"(6)(7)(8)(9)(0)\n"+" \n"+"$00 $10 $20 $30 $40 \n"+"$50 $60 $70 $80 $90 \n"
			+"£00 £10 £20 £30 £40 \n"+"£50 £60 £70 £80 £90 \n"+"ƒ00 ƒ10 ƒ20 ƒ30 ƒ40 \n"+"ƒ50 ƒ60 ƒ70 ƒ80 ƒ90\n"
			+"00¢ 11¢ 22¢ 33¢ 44¢\n"+"55¢ 66¢ 77¢ 88¢ 99¢\n"+" \n"+"00% 0‰ 0-0.0,0…0° \n"+"11% 1‰ 1-1.1,1…1°\n"
			+"00% 0‰ 0-0.0,0…0° \n"+"12% 2‰ 2-2.2,2…2°\n"+"13% 3‰ 3-3.3,3…3°\n"+"11% 1‰ 1-1.1,1…1°\n"
			+"14% 4‰ 4-4.4,4…4°\n"+"15% 5‰ 5-5.5,5…5°\n"+"16% 6‰ 6-6.6,6…6°\n"+"11% 1‰ 1-1.1,1…1°\n"
			+"17% 7‰ 7-7.7,7…7°\n"+"18% 8‰ 8-8.8,8…8°\n"+"19% 9‰ 9-9.9,9…9°\n"+"\n";
	public static final String FONT_FALLBACK_TEST = "Latin\n"+"ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
			+"abcdefghijklmnopqrstuvwxyz\n"+"\n"+"Latin 1 (Western)\n"+"ÁÀÂÄÅÃÆÇÐÉÈÊËÍÌÎÏÑÓÒÔÖÕØŒÞÚÙÛÜÝŸ\n"
			+"áàâäãåæçðéèêëíìîïıñóòôöõøœßþúùûüýÿ\n"+"\n"+"Latin 2 (Eastern)\n"
			+"ĀĂĄĆČĎĐĒĖĘĚĞĢĪĮİĶŁĹĻĽŃŅŇŌŐŔŖŘŠŚŞȘŢȚŤŪŮŰŲŽŹŻ\n"+"āăąćčďđēėęěğģīįķłĺļľńņňōőŕŗřšśşșţțťūůűųžźż\n"+"\n"
			+"Greek (Modern)\n"+"ΑΒΓ∆ΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩΆΈΉΊΌΎΏΪΫ\n"+"αβγδεζηθικλµνξοπρςστυφχψωάέήίόύώϊϋΐΰ\n"
			+"\n"+"Cyrillic 1 (Russian)\n"+"АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ\n"
			+"абвгдежзийклмнопрстуфхцчшщъыьэюя\n"+"\n"+"Cyrillic 2 (Extended)\n"
			+"ЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏҐӁƏҒҖҚҢҮҰҲҶҺӘӢӨӮ\n"+"ѐёђѓєѕіїјљњћќѝўџґӂǝғҗқңүұҳҷһәӣөӯ"+"\n"+"Japanese\n"
			+"Grade 1\n"+"一 九 七 二 人 入 八 力 十 下 三 千 上 口 土 夕 大 女 子 小 山 川 五 天 中 六 円 手 文 \n"
			+"日 月 木 水 火 犬 王 正 出 本 右 四 左 玉 生 田 白 目 石 立 百 年 休 先 名 字 早 気 竹 糸 \n"
			+"耳 虫 村 男 町 花 見 貝 赤 足 車 学 林 空 金 雨 青 草 音 校 森\n"+"Grade 2\n"
			+"刀 万 丸 才 工 弓 内 午 少 元 今 公 分 切 友 太 引 心 戸 方 止 毛 父 牛 半 市 北 古 台 兄 \n"
			+"冬 外 広 母 用 矢 交 会 合 同 回 寺 地 多 光 当 毎 池 米 羽 考 肉 自 色 行 西 来 何 作 体 弟 \n"
			+"図 声 売 形 汽 社 角 言 谷 走 近 里 麦 画 東 京 夜 直 国 姉 妹 岩 店 明 歩 知 長 門 昼 前 南 \n"
			+"点 室 後 春 星 海 活 思 科 秋 茶 計 風 食 首 夏 弱 原 家 帰 時 紙 書 記 通 馬 高 強 教 理 細 \n"
			+"組 船 週 野 雪 魚 鳥 黄 黒 場 晴 答 絵 買 朝 道 番 間 雲 園 数 新 楽 話 遠 電 鳴 歌 算 語 読 \n"+"聞 線 親 頭 曜 顔\n"+"Grade 3\n"
			+"丁 予 化 区 反 央 平 申 世 由 氷 主 仕 他 代 写 号 去 打 皮 皿 礼 両 曲 向 州 全 次 安 守\n"
			+" 式 死 列 羊 有 血 住 助 医 君 坂 局 役 投 対 決 究 豆 身 返 表 事 育 使 命 味 幸 始 実 定\n"
			+" 岸 所 放 昔 板 泳 注 波 油 受 物 具 委 和 者 取 服 苦 重 乗 係 品 客 県 屋 炭 度 待 急 指\n"
			+" 持 拾 昭 相 柱 洋 畑 界 発 研 神 秒 級 美 負 送 追 面 島 勉 倍 真 員 宮 庫 庭 旅 根 酒 消\n"
			+" 流 病 息 荷 起 速 配 院 悪 商 動 宿 帳 族 深 球 祭 第 笛 終 習 転 進 都 部 問 章 寒 暑 植\n"
			+" 温 湖 港 湯 登 短 童 等 筆 着 期 勝 葉 落 軽 運 遊 開 階 陽 集 悲 飲 歯 業 感 想 暗 漢 福\n"
			+" 詩 路 農 鉄 意 様 緑 練 銀 駅 鼻 横 箱 談 調 橋 整 薬 館 題"+"\nChinese\n"+"視申父泉禁名時資題敬足仲与京周可初明染下。克東験未音際保哉木大卓俳世。\n"
			+"図反署必益明属前判庫男藤染米律児最特選描。事航版設丸井株需意登運名広。\n"+"活率閣探紅容声安目青谷知激武講介。況取密満論訳朝長写端買総冬。逮会朝咲不朝温村験食公納生歩。\n"
			+"京根狭書値裁給事認室健超幼読共法改。索洋物幅意写鳥輩全訴写考盟務少。";
	public static final Icon FOLDER_ICON = DarkUIUtil.ICON_LOADER.getIcon("files/folder.svg", 19, 19, true);
}
