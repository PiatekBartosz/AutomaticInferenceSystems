% There is given a factbase from a library with the following facts:
%	item(<type>,<item_identity_number>,<title>,<scientific_field>) 
%	person(<person_identity_number>,<name>)
%	borrowed(<item_identity_number>,<person_identity_number>)
% Add at least two unique facts using Your names.

% Add to the program rules task(1), task(2) etc., to achieve goals given by a tutor.
% Notice: Write the program in such a way, that all solutions for a single goal are found 
% in one step (without clicking "more/next") and are single (they cannot repeat). 
% For all goals the program must end with "yes/true".

% Report should contain codes of program + answers to tasks (in text form, not screenshots!)


% facts

item(book,1001,"Algebra's Secrets",mathematics).
item(book,1002,"Inorganic Chemistry",chemistry).
item(book,1003,"Mathematical Analysis Tasks",mathematics).
item(book,1004,"Fundamentals of Organic Chemistry",chemistry).
item(book,1005,"Probability and Statistics",mathematics).
item(book,1006,"Quantum Mechanics",physics).
item(book,1007,"Physics for Blondes",physics).	
	
item(journal,2001,"Acta Arithmetica",mathematics).
item(journal,2002,"Advances in Natural Philosophy",physics).
item(journal,2003,"Studia Mathematica",mathematics).
item(journal,2004,"Chemik Polski",chemistry).
item(journal,2005,"Progress in Physics",physics).
item(journal,2006,"Polish Journal of Chemistry",chemistry).
item(journal,2007,"Chemistry Market",chemistry).
	
person(1,"Jan Kowalski").
person(2,"Anna Nowak").
person(3,"Maciej Mazur").
person(4,"Zenon Kwiatkowski").
person(5,"Katarzyna Dudek").
person(6,"Jakub Adamczyk").
person(7, "Bartosz Piatek").
person(8, "Gabriel Malanowski").	

borrowed(1001,1).
borrowed(1002,1).
borrowed(2001,1).
borrowed(1004,2).
borrowed(1005,4).
borrowed(2003,4).
borrowed(2005,5).
borrowed(2006,5).

	
% rules

---------------------------------------

% Bartosz Piatek, Gabriel Malanowski

program:-
	writeln("Give the number of task: "),
	read(N),
	task(N).

	
task(1):- 
    person(Id, Name),
    write(Id),
    write(" "),
    writeln(Name),
    fail.
task(1).

% Result: 1 Jan Kowalski
% 2 Anna Nowak
% 3 Maciej Mazur
% 4 Zenon Kwiatkowski
% 5 Katarzyna Dudek
% 6 Jakub Adamczyk
% 7 Bartosz Piatek
% 8 Gabriel Malanowski
% true

----------------------------------------

task(2):-
    person(Id, Name),
	check_id_book(Id),
    write(Id),
    write(" "),
    writeln(Name),
    fail.

task(2).

check_id_book(Id):-
    borrowed(Item_id, Id),
    item(Type, Item_id, _, _),
    Type = book, !.

% Result:
% 1 Jan Kowalski
% 2 Anna Nowak
% 4 Zenon Kwiatkowski
% true

----------------------------------------

task3 :-
    person(Id, Name),
    task3_tmp(Id),
    write(Id), 
    write(" | "),
    write(Name), nl,
    fail.
task3.

task3_tmp(Id) :- 
    borrowed(Idbook1, Id),
    borrowed(Idbook2, Id),
    Idbook1 \== Idbook2, !.

% Result:
% 1 | Jan Kowalski
% 4 | Zenon Kwiatkowski
% 5 | Katarzyna Dudek
% true

----------------------------------------

task4_tmp(Id) :-
    borrowed(Idbook1, Id),
    borrowed(Idbook2, Id),
    Idbook1 \== Idbook2, !.
task4_tmp1(Id) :-
    borrowed(Idbook1, Id),
    borrowed(Idbook2, Id),
    borrowed(Idbook3, Id),
    Idbook1 \== Idbook2,
    Idbook1 \== Idbook3,
    Idbook2 \== Idbook3, !.
task4 :-
    person(Id, Name),
    task4_tmp(Id),
    not(task4_tmp1(Id)),
    write(Id), 
    write(" | "),
    write(Name), nl,
    fail.
task4.
  
% Result:   
% 4 | Zenon Kwiatkowski
% 5 | Katarzyna Dudek
% true
    
    

    
