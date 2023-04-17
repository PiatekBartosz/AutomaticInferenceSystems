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
	
borrowed(1001,1).
borrowed(1002,1).
borrowed(2001,1).
borrowed(1004,2).
borrowed(1005,4).
borrowed(2003,4).
borrowed(2005,5).
borrowed(2006,5).

% ---------------------------------------------------------------------------
Wykonanie: Gabriel Malanowski, Bartosz Piątek
% ---------------------------------------------------------------------------

% task 1

task1:- 
    findall(Id, borrowed(_,Id), IdList),
    list_to_set(IdList, IdSet),
    Acc = [],
    print_name(IdSet, Acc, NameSet),
    write(NameSet).
print_name([], Acc, NameSet) :- NameSet = Acc, !.
print_name([H|T], Acc, NameSet) :-
    person(H, Name),
    print_name(T, [Name|Acc], NameSet).

% result
[Katarzyna Dudek, Zenon Kwiatkowski, Anna Nowak, Jan Kowalski]
true

% ---------------------------------------------------------------------------

% task 2
task2:-
    findall([Type, Title], item(Type,_,Title,chemistry), List),
    bubble_sortTitle(List,SortedList),
    bubble_sortType(SortedList, SortedList1),
    write(SortedList1).
    
bubble_sortTitle(LP,LK):-
    append(X,[[H11,H12],[H21,H22]|T],LP),
    H22 @< H12,!,
    append(X,[[H21,H22],[H11,H12]|T],LW),
    bubble_sortTitle(LW,LK).
bubble_sortTitle(LK,LK).

bubble_sortType(LP,LK):-
    append(X,[[H11,H12],[H21,H22]|T],LP),
    H11 @> H21,!,
    append(X,[[H21,H22],[H11,H12]|T],LW),
    bubble_sortType(LW,LK).
bubble_sortType(LK,LK).

% result 
[book, Fundamentals of Organic Chemistry], [book, Inorganic Chemistry], [journal, Chemik Polski], [journal, Chemistry Market], [journal, Polish Journal of Chemistry]]
true

% ---------------------------------------------------------------------------

% task 3

bitlist(L1, L):-
    Acc = [],
    m(L1, Acc, L).

m(0, Acc, L) :- L = Acc, !.
m(L1, Acc, L) :-
    X is mod(L1,2),
    Y is floor(L1/2),
    m(Y, [X|Acc], L).

% result
bitlist(57, List).
List = [1, 1, 1, 0, 0, 1]

% ---------------------------------------------------------------------------

% task 4

rev_bits(L1, R) :-
    Acc = [],
    reverse_bits(L1, Acc, R1),
    reverse(R1, R).

reverse_bits([], Acc, R) :- R = Acc, !.
reverse_bits([0| T], Acc, R) :-
    reverse_bits(T, [1|Acc], R).
reverse_bits([1| T], Acc, R) :-
    reverse_bits(T, [0|Acc], R).

% result 
rev_bits([1, 0, 0, 1, 1], R).
R = [0, 1, 1, 0, 0]





