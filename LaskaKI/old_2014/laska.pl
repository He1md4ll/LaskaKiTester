:- dynamic
	pMove/2.
:- dynamic
	pJump/2.
:- dynamic
	jumpStartFeld/1.
:- dynamic
	aktuellerZug/1.
:- dynamic
	amZug/1.
:- dynamic
	wertung/2.
:- dynamic
	simbrett/2.
:- dynamic
	brett/2.	
:- dynamic
	kizeit/1.	

%Speichert die komplette Ausgabe in einer .txt-Datei	
:- protocol('mylog.txt').
	
kizeit(0). %Speicherung der abgelaufenen Berechnungszeit
amZug(schwarz). %Schwarz beginnt das Spiel

:- ['board.pl']. %Einbindung des Boards

%Durchz�hlen der Z�ge. Hat keinen  Einfluss auf die KI. Nur zur Anzeige.
aktuellerZug(1).
aktuellerZug(X) :- aktuellerZug(Y), X is Y+1.

%Mainloop
start:-
	aktuellerZug(N),write('-> '),write(N),nl,  % Nummer-Ausgabe aktueller Zug. Zudem Schleifen-Funktionalit�t �ber aktuellerZug
	amZug(Farbe),                              % Holen der aktuellen Farbe
	loescheMoves,                              % L�schen der m�glichen Z�ge von der vorherigen Runde
	loescheJumps,                              % L�schen der m�glichen Spr�nge von der vorherigen Runde
	schreibeBrett(Farbe),					   % Ausgabe des Spielfeldes
	holen(Farbe),							   % Holen der aktuell m�glichen Z�ge f�r die Ausgabe
	checkWinner(Farbe),						   % �berpr�fen ob das Spiel vorbei ist
	handleSchlagzwang([]),                     % L�schen von normalen Z�gen wenn Spr�nge vorhanden
	loescheZwischenJumps([]),                  % L�schen von unn�tigen Spr�ngen - Spr�nge, nach denen noch weitergesprungen werden kann
	schreibe,								   % Ausgabe der m�glichen Z�ge
	getZug(Farbe,Zugfolge),					   % Auswahl des Zuges, entweder KI oder Manuelle Eingabe
	ziehen(Farbe,Zugfolge,[]),                 % Durchf�hren des Zuges
	switchFarbe(Farbe),                        % Farbwechsel
	fail.									   % Redo.

	
% Dynamische Berechnung der Suchtiefe
getTiefe(Tiefe):-   
						aggregate_all(count, brett(_,[r|_]), R), %Z�hlen der Offiziere
						aggregate_all(count, brett(_,[g|_]), G), 
						R + G > 2,                               % Ab 3 Offizieren im Spiel, Suchetiefe auf 4 setzen
						Tiefe is 4,!
					; 
						aggregate_all(count, brett(_,[]), E),    % Z�hlen der leeren Felder
						(                                        % Anpassen der Suchtiefe �ber die Anzahl der leeren Felder
							E > 12, Tiefe is 5
						;	
							E > 10, Tiefe is 6
						;
							E > 8, Tiefe is 7
						;
							Tiefe is 8
						),!.

%Loeschen aller gespeicherten m�glichen Z�ge und Spr�nge
loescheMoves :- retract(pMove(_,_)),fail;!.
loescheJumps :- retract(pJump(_,_)),fail;!.

% Farbwechsel
switchFarbe(Farbe) :-   retract(amZug(Farbe)),
						andereFarbe(Farbe,Andere),
						asserta(amZug(Andere)),!.

andereFarbe(schwarz,weiss).
andereFarbe(weiss,schwarz).


% Auswahl des Zuges 
% �bers Ein- und Auskommentieren lassen sich jede Art von Spiel spielen: 
% Mensch gegen Computer, Mensch gegen Mensch, Computer gegen Computer

% Auswahl durch KI, wenn weiss am Zug
getZug(weiss,Zugfolge) :- 	getTiefe(Tiefe), write('Tiefe:'),write(Tiefe),nl, 			 % Holen der Tiefe
							statistics(walltime,_),										 % Start Zeitmessung
							askKI(Zugfolge,Tiefe),										 % Berechnung des besten Zuges durch die KI
							statistics(walltime,[_,NZeit]),								 % Ende Zeitmessung
							kizeit(AZeit),                                                  % Holen der aktuellen Summenzeit
							AkkkumiliereZeit is NZeit + AZeit,							 % Addieren der Zeiten
							asserta(kizeit(AkkkumiliereZeit)),retract(kizeit(AZeit)),	 % und speichern
							write('Zeit in MilliSekunden:'),write(AkkkumiliereZeit),nl,	 % Ausgabe Zeit
							Sek is AkkkumiliereZeit/1000,                               
							write('Sekunden:'),write(Sek),nl,                           
							write('KI -> '),write(Zugfolge),nl,!.						 % Ausgabe des ausgew�hlten Zuges

% Auswahl durch KI, wenn schwarz am Zug, wie oben
%getZug(schwarz,Zugfolge) :- getTiefe(Tiefe),write('Tiefe:'),write(Tiefe),nl,statistics(walltime,_),
%							askKI(Zugfolge,Tiefe),statistics(walltime,[_,NZeit]),kizeit(AZeit),
%							AkkkumiliereZeit is NZeit + AZeit,asserta(kizeit(AkkkumiliereZeit)),
%							retract(kizeit(AZeit)),write('Zeit in MilliSekunden:'),write(AkkkumiliereZeit),nl,Sek is AkkkumiliereZeit/1000, 
%							write('Zeit in Sekunden:'),write(Sek),nl,write('KI -> '),write(Zugfolge),nl,!.

% Menschliche Eingabe des Zuges
%getZug(weiss,Zugfolge) :- read(Zugfolge),!.
getZug(schwarz,Zugfolge) :- read(Zugfolge),!.

% Berechnung des besten Zuges
askKI(Beste,Tiefe) :- 	speicherbrett, 								% Speichern des aktuellen Bretts
						clear,										% L�schen Alter Z�ge
						startZweig([],Tiefe,Wertung,-20000,20000),  % Starten des Suchbaums, in Wertung wird die Wertung des besten Zuges gespeichert
						getBeste(Beste,Wertung),					% Holen des besten Zuges
						ladebrett,!.								% Wiederherstellen des urspr�nglichen Bretts

% Auswahl des Zuges zur angegebenen Wertung
getBeste(Zugfolge,Wertung) :- aggregate_all(count, wertung([A|[]],Wertung), C), 	% Z�hlen der Z�ge mit angegebenen Wertung  
						(
							C == 1,wertung([Zugfolge|[]],Wertung)					% Wenn nur einer vorhanden, direkte R�ckgabe
						; 															% Bei mehreren, Zuf�llige Auswahl vom ersten oder Zweiten
							random_between(0,1,R),
							(
								R == 1,
								wertung([Zugfolge|[]],Wertung)
							;
								wertung([A|[]],Wertung),wertung([B|[]],Wertung),B \= A,Zugfolge = B
							)
						),!.
						

% L�schen aller m�glichen Z�ge und Spr�nge und gespeicherten Wertungen						
clear :- (pMove(_,_),retract(pMove(_,_));pJump(_,_),retract(pJump(_,_));wertung(_,_),retract(wertung(_,_))),fail;!.

% Wenn Endtiefe erreicht, Wertung berrechnen und R�ckgabe
startZweig(Zugfolge,0,Wertung,_,_) :- simuliere(Zugfolge),
								  bewerte(Wertung,Zugfolge),!.
								  %,write(Zugfolge),write('-'),write(Wertung),nl,!. %Debug Ausgabe

% Suchebaum. Der Suchbaum wird rekursiv aufgebaut mit Alpha Beta
startZweig(Zugfolge,Laenge,Wertung,Alpha,Beta) :- 
	simuliere(Zugfolge),									% Aufbau des Brettes zum Zeitpunkt des Zweiges
	farbeNachZug(Zugfolge,Farbe),							% Berechnung der Farbe zum Zeitpunkt des Zweiges
	holenKI(Farbe,Zugfolge),								% Berechnen der m�glichen Z�ge zum Zeitpunkt des Zweiges
	handleSchlagzwang(Zugfolge),							% L�schen von normalen Z�gen wenn Spr�nge vorhanden
	loescheZwischenJumps(Zugfolge),                         % L�schen von unn�tigen Spr�ngen - Spr�nge, nach denen noch weitergesprungen werden kann
		(Zugfolge == [],onlyOneMove,Wertung is 10001;(   	% Wenn im Stamm nur ein Zug m�glich ist, sofortiger Abbruch und Auswahl dessen
		(pMove(Zug,Zugfolge);pJump(Zug,Zugfolge)),			% Zu jedem der gefundenen Z�ge ein neuen Zweig starten
		append(Zugfolge,[Zug],NeueZugfolge),				
		L is Laenge-1,
		( 													
			minOrMax(Farbe,max),							% Max-Knoten
			( 												
				wertung(Zugfolge,Vergleich),				% Zum aktuellen Zweig wurde schonmal eine Wertung gespeichert
				( 
					Vergleich > Beta						% Dieser gespeicherter Wert ist gr��er als Beta
				;	
					Vergleich =< Beta,						% oder kleiner-gleich
					(Vergleich > Alpha,NewAlpha is Vergleich;Vergleich =< Alpha,NewAlpha is Alpha), % Auswahl des Alphawerts
					startZweig(NeueZugfolge,L,Wert,NewAlpha,Beta), % Starten des neuen Zweigs
					(										% Nach der R�ckkehr vom neuen Zweig
						Wert > Vergleich,					% Wertung des Zweigs ist Gr��er als der vorherige	
						retract(wertung(Zugfolge,_)),		% Speichern der neuen Wertung
						asserta(wertung(Zugfolge,Wert))
					; 										% Wenn nicht gr��er, dann nicht
						true	
					)
				)
			; 												
				\+wertung(Zugfolge,_),						% Zum aktuellen Zweig wurde noch keine Wertung gespeichert
				startZweig(NeueZugfolge,L,Wert,Alpha,Beta), % direkt neuen Zweig starten
				asserta(wertung(Zugfolge,Wert))				% und die Wertung direkt speichern
			)
		; 													
			minOrMax(Farbe,min),							% Min-Knoten
			(
				wertung(Zugfolge,Vergleich),                 % Zum aktuellen Zweig wurde schonmal eine Wertung gespeichert
				(                                            
					Vergleich < Alpha                        % Dieser gespeicherter Wert ist kleiner als Alpha
				;	                                         
					Vergleich >= Alpha,                      % oder gr��er-gleich
					(Vergleich < Beta,NewBeta is Vergleich;Vergleich >= Beta,NewBeta is Beta), % Auswahl des Betawerts
					startZweig(NeueZugfolge,L,Wert,Alpha,NewBeta),  % Starten des neuen Zweigs
					(	                                      % Nach der R�ckkehr vom neuen Zweig
						Wert < Vergleich,                     % Wertung des Zweigs ist kleiner als der vorherige
						retract(wertung(Zugfolge,_)),         % Speichern der neuen Wertung
						asserta(wertung(Zugfolge,Wert))       
					;                                         % Wenn nicht kleiner, dann nicht
						true
					)
				)
			; 
				\+wertung(Zugfolge,_),                          % Zum aktuellen Zweig wurde noch keine Wertung gespeichert
				startZweig(NeueZugfolge,L,Wert,Alpha,Beta),     % direkt neuen Zweig starten
				asserta(wertung(Zugfolge,Wert))                 % und die Wertung direkt speichern
			)
		),
		fail													% Redo, f�r jeden der Z�ge und Spr�nge
	;wertung(Zugfolge,Wertung),! 								% R�ckgabe Wertung bei nur ein Zug im Stamm
	%,write(Zugfolge),write('9-'),write(Wertung),nl,!			% Debug-Ausgabe
	;keinZugWertung(Wertung,Zugfolge),! 						% Kein Zug m�glich, Sieg oder Niederlage
	%,write(Zugfolge),write('#-'),write(Wertung),nl,!			% Debug-Ausgabe
	)),!.

% Brechnung der Wertung wenn keine Z�ge mehr m�glich. -10000 bei Niederlage, 10000 bei Sieg
keinZugWertung(Wertung,Zugfolge) :- farbeNachZug(Zugfolge,Farbe),(amZug(Farbe),Wertung is -10000;Wertung is 10000),asserta(wertung(Zugfolge,Wertung)),!.

% �berpr�fung ob nur noch ein Zug m�glich. Speicherung des Zuges mit einer Wertung 10001
onlyOneMove :- aggregate_all(count, pMove(_,[]), M),
			   aggregate_all(count, pJump(_,[]), J),!,
			   M + J < 2,(pMove(Zug,[]);pJump(Zug,[])),
			   asserta(wertung([Zug],10001)),!.

% �berpr�fung ob Min Oder Max-Knoten			   
minOrMax(Farbe,max) :- amZug(Farbe),!.
minOrMax(Farbe,min) :- \+amZug(Farbe),!.

% Berechnung, wer am Zug ist nach der angegebenen Zugfolgenliste.
farbeNachZug(Zugfolge,Farbe) :- amZug(Vorher),
								length(Zugfolge,L),
								(gerade(L),                		% gerade Anzahl an Z�ge -> Farbe bleibt
								Farbe = Vorher;
								andereFarbe(Vorher,Andere),     % ungerade Anzahl an Z�ge -> Farbe bleibt
								Farbe = Andere),!.

								
% Einfache gerade und ungerade �berpr�fung 
% von http://wwwlehre.dhbw-stuttgart.de/~reichard/content/vorlesungen/wbs/loesungen/prolog_loesungen_2.pl
gerade(0).
gerade(X):-X>0,Z is X - 1,ungerade(Z).
ungerade(1).
ungerade(X):-X>1,Z is X - 1,gerade(Z).
	
% Bewertungsfunktion: 
% Anzahl der eigenen normalen Figur * 20 + Anzahl der eigenen Offiziere * 65 + M�gliche eigene Z�ge * 3 + M�gliche eigene Spr�nge * 3
% - Anzahl der gegnerischen normalen Figur * 20 - Anzahl der gegnerischen Offiziere * 65 - M�gliche gegnerische Z�ge * 3 - M�gliche gegnerische Spr�nge * 3
bewerte(Bewertung,Zugfolge) :- 	   amZug(Farbe),
								   aggregate_all(count, brett(_,[s|_]), S),
								   aggregate_all(count, brett(_,[w|_]), W),
								   aggregate_all(count, brett(_,[r|_]), R),
								   aggregate_all(count, brett(_,[g|_]), G),
								   holenKI(Farbe,Zugfolge),
								   %handleSchlagzwang(Zugfolge),
								   loescheZwischenJumps(Zugfolge),
                                   aggregate_all(count, pMove(_,Zugfolge), M),
                                   aggregate_all(count, pJump(_,Zugfolge), J),
								   loescheZuege(Zugfolge),
								   andereFarbe(Andere,Farbe),
								   holenKI(Andere,Zugfolge),
								   %handleSchlagzwang(Zugfolge),
								   loescheZwischenJumps(Zugfolge),
                                   aggregate_all(count, pMove(_,Zugfolge), GM),
                                   aggregate_all(count, pJump(_,Zugfolge), GJ),							
								   (M == 0, J == 0, Bewertung is -10000;
								   GM == 0, GJ == 0, Bewertung is 10000;
								   (Farbe == schwarz,
								   Bewertung is 20*S-20*W+65*R-65*G+3*M+3*J-3*GM-3*GJ;
								   Bewertung is 20*W-20*S-65*R+65*G+3*M+3*J-3*GM-3*GJ)),!.
								   

% L�sche Z�ge zur angegebenen Zugfolge
loescheZuege(Zugfolge) :- pMove(_,Zugfolge),retract(pMove(_,Zugfolge)),fail;
						  pJump(_,Zugfolge),retract(pJump(_,Zugfolge)),fail;!.
								   
% Aufbauen des Brettes nach der Zugefolge
simuliere(Zugfolge) :- ladebrett,simzuege(Zugfolge),!.

% Rekursives Durchf�hren einer Zugfolgen-Liste
simzuege([]).
simzuege([Head|Tail]) :- ziehenOhne(Head),simzuege(Tail).

% Zwischenspeichern des Bretts
speicherbrett :- retract(simbrett(_,_)),fail;(brett(Feld,Liste),asserta(simbrett(Feld,Liste)),fail;true).

% Laden des Bretts aus dem Zwischenspeicher
ladebrett :- retract(brett(_,_)),fail;(simbrett(Feld,Liste),asserta(brett(Feld,Liste)),fail;true).

% Ausgabe der m�glichen Z�ge
schreibe :- (pMove(X,[]);pJump(X,[])),
	write(X),nl, fail.
schreibe.

% Berechne der m�glichen Z�ge
holen(Farbe) :- holeZuege(Farbe,[]),fail.
holen(_).

% Berechne der m�glichen Z�ge zu einem Zeitpunkt bei der Berechnung der KI
holenKI(Farbe,Startfolge) :- holeZuege(Farbe,Startfolge),fail.
holenKI(_,_).

% Berechnen der Z�ge
holeZuege(Farbe,Startfolge) :-
	transform(Farbe,TFarbe),
	brett(Feld,[TFarbe|_]),
	(testMove(Farbe,TFarbe,Feld,FeldZ),atom_concat(Feld,FeldZ,Move),asserta(pMove(Move,Startfolge));
	abolish(jumpStartFeld/1),asserta(jumpStartFeld(Feld)),testSpruenge(Farbe,TFarbe,Feld,[],Feld,Startfolge)
	).

% L�sche normale Z�ge wenn ein Sprung vorhanden
handleSchlagzwang(Startfolge) :- pJump(_,Startfolge),(retract(pMove(_,Startfolge)),fail);!.
handleSchlagzwang(Startfolge).

% L�sche Spr�nge, nach denen ein weiterer Sprung noch m�glich ist. 
% Bei der aktuellen Berechnung der m�glichen Spr�nge werden diese noch mit angegeben. 
loescheZwischenJumps(Startfolge) :- loescheZwischenJumps2(Startfolge),!.
loescheZwischenJumps(Startfolge).
loescheZwischenJumps2(Startfolge) :- 
	pJump(J1,Startfolge),pJump(J2,Startfolge)
	,atom_length(J1,L1),atom_length(J2,L2)
	,L2 > L1,sub_atom(J2,0,L1,_,Erg),
	Erg == J1,retract(pJump(J1,Startfolge)),fail.


% Suchen nach Spr�ngen. Gefundene Spr�nge werden mit "asserta" hinzugef�gt.
testSpruenge(Farbe,TFarbe,Start,MListe,Erg,Zugfolge) :- 
testJump(Farbe,TFarbe,Start,Ziel,M),\+member(M,MListe),atom_concat(Erg,Ziel,NeuErg),
testSpruenge(Farbe,TFarbe,Ziel,[M|MListe],NeuErg,Zugfolge);atom_length(Erg,L),L >= 4,asserta(pJump(Erg,Zugfolge)).

% Durchf�hren des Zuges
ziehen(Farbe,Zugfolge,Startfolge) :-   
	(pMove(Zugfolge,Startfolge),sub_atom(Zugfolge,0,2,_,FeldA),
	sub_atom(Zugfolge,2,2,_,FeldZ),move(FeldA,FeldZ);(pJump(Zugfolge,Startfolge),springen(Zugfolge))),!,handlePromotion(Zugfolge).
ziehen(Farbe,_,_) :-
	nl,write('Ung�ltige Eingabe!'),nl,nl,fail.

% Durchf�hren eines Zuges ohne �berpr�fung auf Richtigkeit
ziehenOhne(Zugfolge) :-
	sub_atom(Zugfolge,0,2,_,FeldA),
	sub_atom(Zugfolge,2,2,_,FeldZ),
	(sindnachbarn(FeldA,FeldZ),move(FeldA,FeldZ);
	springen(Zugfolge)),!,handlePromotion(Zugfolge).

% �berpr�fung ob FeldA und FeldB aneinander grenzen.
sindnachbarn(FeldA,FeldB) :- nachbarn(FeldA,FeldB,_);nachbarn(_,FeldB,FeldA);nachbarn(_,FeldA,FeldB);nachbarn(FeldB,FeldA,_).

% Ist das Feld mit einer eigenen Figur besetzt.
selbst(schwarz,Feld,s) :-
	brett(Feld,[s|_]).
selbst(schwarz,Feld,r) :-
	brett(Feld,[r|_]).
selbst(weiss,Feld,w) :-
	brett(Feld,[w|_]).
selbst(weiss,Feld,g) :-
	brett(Feld,[g|_]).

% Angabe, wer wen schlagen darf.
opponent(w,s).
opponent(w,r).
opponent(s,w).
opponent(s,g).
opponent(r,g).
opponent(g,r).
opponent(g,s).
opponent(r,w).

% �berpr�fung ob normaler Zug m�glich
testMove(schwarz,Kopf,FeldA,FeldZ) :-
	( nachbarn(FeldZ,FeldA,_)
	; Kopf == r, nachbarn(FeldA,FeldZ,_)
	),
	brett(FeldZ,[]).
testMove(weiss,Kopf,FeldA,FeldZ) :-
	( nachbarn(FeldA,FeldZ,_)
	; Kopf == g, nachbarn(FeldZ,FeldA,_)
	),
	brett(FeldZ,[]).

% Durchf�hren eines Zugs
move(FeldA,FeldZ) :-
	retract(brett(FeldA,Stapel)),
	assertz(brett(FeldA,[])),
	retract(brett(FeldZ,[])),
	asserta(brett(FeldZ,Stapel)).

% �berpr�fen ob Sprung m�glich
testJump(schwarz,Kopf,FeldA,FeldZ,M) :-
	( nachbarn(FeldZ,M,FeldA)
	; Kopf == r, nachbarn(FeldA,M,FeldZ)
	),
	brett(M,[Mkopf|_]),
	opponent(Kopf,Mkopf),
	(brett(FeldZ,[]);jumpStartFeld(FeldZ)).
testJump(weiss,Kopf,FeldA,FeldZ,M) :-
	( nachbarn(FeldA,M,FeldZ)
	; Kopf == g, nachbarn(FeldZ,M,FeldA)
	),
	brett(M,[Mkopf|_]),
	opponent(Kopf,Mkopf),
	brett(FeldZ,[]).

% Rekursives Durchf�hren einer Sprungkette
springen(Move):-
	atom_length(Move,L),
	sub_atom(Move,0,2,_,FeldA),
	sub_atom(Move,2,2,_,FeldZ),
	(nachbarn(FeldA,M,FeldZ);nachbarn(FeldZ,M,FeldA)),
	jump(FeldA,M,FeldZ),
	(L > 4, 
	R is L - 2,
	sub_atom(Move,2,R,_,Neu),
	springen(Neu)
	;L < 5).
	

% Durchf�hren eines Sprunges	
jump(FeldA,M,FeldZ) :-
	retract(brett(FeldA,StapelA)),
	assertz(brett(FeldA,[])),
	retract(brett(M,[KopfM|RestM])),
	asserta(brett(M,RestM)),
	retract(brett(FeldZ,[])),
	append(StapelA,[KopfM],StapelZ),
	asserta(brett(FeldZ,StapelZ)).

% Durchf�hren der Bef�rderung
handlePromotion(Zugfolge):-
atom_length(Zugfolge,L),L2 is L-2,
sub_atom(Zugfolge,L2,2,_,F),
brett(F,[Head|_]),
transform(Farbe,Head),
handlePromotion2(Farbe,Zugfolge),!.
handlePromotion2(weiss,Zugfolge) :- 
	atom_length(Zugfolge,L),L2 is L-2,
	sub_atom(Zugfolge,L2,1,_,F),
	F == g,
	sub_atom(Zugfolge,L2,2,_,Ziel),
	brett(Ziel,[Head|Tail]),
	retract(brett(Ziel,[Head|Tail])),
	asserta(brett(Ziel,[g|Tail])).
handlePromotion2(schwarz,Zugfolge) :- 
	atom_length(Zugfolge,L),L2 is L-2,
	sub_atom(Zugfolge,L2,1,_,F),
	F == a,
	sub_atom(Zugfolge,L2,2,_,Ziel),
	brett(Ziel,[Head|Tail]),
	retract(brett(Ziel,[Head|Tail])),
	asserta(brett(Ziel,[r|Tail])).	
handlePromotion2(_,_).


% Umwandlung von ausgeschriebener Farbe in Format der Felder
transform(schwarz,s).
transform(schwarz,r).
transform(weiss,w).
transform(weiss,g).

% �berpr�fung ob Spiel vorbei und wer gewonnen hat und Ausgabe.
checkWinner(weiss) :- \+pJump(_,[]),\+pMove(_,[]),write('Schwarz ist der Sieger!!'),abort.
checkWinner(schwarz) :- \+pJump(_,[]),\+pMove(_,[]),write('Weiss ist der Sieger!!'),abort.
checkWinner(_).
