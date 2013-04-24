Advanced Algorithmics 2013 – Hands-on
========

This repository is for contributions to the hands-on exercises of the summer 2013 run of our Advanced Algorithmics course.
These are the scenarios we investigate:

### Zombies in Kaiserslautern

Zombies are loose! Lucky for us, a non-trivial amount of military is close by
and has an interest in pacifying Kaiserslautern. They have limited resources, though,
and we therefore need to develop an efficient deployment plan.

We have the following restrictions:

 * Patrols are risky, so we favour entrenched positions with all-round vision. 
   We will assume that we can station units on roads and crossroads.
 * We can allow neither the undead nor plunderers any reprieve. Therefore,
   we want to have every stretch of road and crossroads covered.
 * We want to use as few units as possible.

### Clearing snow in Kaiserslautern

As everybody who has spent a winter season in Kaiserslautern knows, our snow-plowing
service has only one vehicle at its disposal. Since this is not nearly enough to 
clear *all* snow away, decisive measures have to be taken. From now on, we 
will no longer try to ensure that every street is cleared, but only that we can 
get everywhere *somehow*.

We need a route for the snow-plower that ensures the following.

 * After the tour is complete, there has to be a snow-clear route from
   every crossroad to every other.
 * The snow-plower takes the fastest route possible.

---

Our roadmap is to

 1. model the problems in abstract terms,
 2. find algorithms in literature that follow the paradigms presented in class and
 3. implement them, comparing performance on artifical and real data.

Find more information on the [course website](http://wwwagak.cs.uni-kl.de/Vorlesung/advanced-algorithmics.html).
