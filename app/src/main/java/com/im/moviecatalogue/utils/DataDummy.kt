package com.im.moviecatalogue.utils
//
//import com.im.moviecatalogue.data.local.entity.MovieEntity
//import com.im.moviecatalogue.utils.values.CategoryEnum
//
//class DataDummy{
//    fun generateDummyMovies(): ArrayList<MovieEntity>{
//        val movies = ArrayList<MovieEntity>()
//
//        movies.add(
//            MovieEntity(
//                id = 1,
//                year = "2019",
//                title = "The Lion King",
//                synopsis = "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/2bXbqYdUdNVa8VIWXVfclP2ICtT.jpg",
//                trailer = "7TavVZMewpY",
//                rate = "72",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 2,
//                year = "2019",
//                title = "Dark Phoenix",
//                synopsis = "The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/cCTJPelKGLhALq3r51A9uMonxKj.jpg",
//                trailer = "QWbMckU3AOQ",
//                rate = "61",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 3,
//                year = "2019",
//                title = "John Wick: Chapter 3 – Parabellum",
//                synopsis = "Super-assassin John Wick returns with a \\\$14 million price tag on his head and an army of bounty-hunting killers on his trail. After killing a member of the shadowy international assassin’s guild, the High Table, John Wick is excommunicado, but the world’s most ruthless hit men and women await his every turn.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/ziEuG1essDuWuC5lpWUaw1uXY2O.jpg",
//                trailer = "M7XM597XO94",
//                rate = "71",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 4,
//                year = "2019",
//                title = "Fast & Furious Presents: Hobbs & Shaw",
//                synopsis = "A spinoff of The Fate of the Furious, focusing on Johnson's US Diplomatic Security Agent Luke Hobbs forming an unlikely alliance with Statham's Deckard Shaw.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/keym7MPn1icW1wWfzMnW3HeuzWU.jpg",
//                trailer = "9SA7FaKxZVI",
//                rate = "65",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 5,
//                year = "2019",
//                title = "Aladdin",
//                synopsis = "A kindhearted street urchin named Aladdin embarks on a magical adventure after finding a lamp that releases a wisecracking genie while a power-hungry Grand Vizier vies for the same lamp that has the power to make their deepest wishes come true.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/3iYQTLGoy7QnjcUYRJy4YrAgGvp.jpg",
//                trailer = "foyufD52aog",
//                rate = "71",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 6,
//                year = "2019",
//                title = "Godzilla: King of the Monsters",
//                synopsis = "Follows the heroic efforts of the crypto-zoological agency Monarch as its members face off against a battery of god-sized monsters, including the mighty Godzilla, who collides with Mothra, Rodan, and his ultimate nemesis, the three-headed King Ghidorah. When these ancient super-species - thought to be mere myths - rise again, they all vie for supremacy, leaving humanity's very existence hanging in the balance.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/pU3bnutJU91u3b4IeRPQTOP8jhV.jpg",
//                trailer = "KDnKuFtdc7A",
//                rate = "62",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 7,
//                year = "2019",
//                title = "Spider-Man: Far from Home",
//                synopsis = "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/lcq8dVxeeOqHvvgcte707K0KVx5.jpg",
//                trailer = "LFoz8ZJWmPs",
//                rate = "78",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 8,
//                year = "2019",
//                title = "Avengers: Endgame",
//                synopsis = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
//                trailer = "hA6hldpSTF8",
//                rate = "83",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 9,
//                year = "2019",
//                title = "Men in Black: International",
//                synopsis = "The Men in Black have always protected the Earth from the scum of the universe. In this new adventure, they tackle their biggest, most global threat to date: a mole in the Men in Black organization.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/dPrUPFcgLfNbmDL8V69vcrTyEfb.jpg",
//                trailer = "BV-WEb2oxLk",
//                rate = "59",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        movies.add(
//            MovieEntity(
//                id = 10,
//                year = "2019",
//                title = "Captain Marvel",
//                synopsis = "The story follows Carol Danvers as she becomes one of the universe’s most powerful heroes when Earth is caught in the middle of a galactic war between two alien races. Set in the 1990s, Captain Marvel is an all-new adventure from a previously unseen period in the history of the Marvel Cinematic Universe.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/aYDRwraSc8SXgot5jabtspJTWZB.jpg",
//                trailer = "Z1BCujX3pw8",
//                rate = "70",
//                category = CategoryEnum.MOVIE.value
//            )
//        )
//        return movies
//    }
//
//    fun generateDummyTVShows(): ArrayList<MovieEntity>{
//        val tvShow = ArrayList<MovieEntity>()
//
//        tvShow.add(
//            MovieEntity(
//                id = 11,
//                year = "2014",
//                title = "The Flash",
//                synopsis = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \\\"meta-human\\\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/fki3kBlwJzFp8QohL43g9ReV455.jpg",
//                trailer = "Yj0l7iGKh8g",
//                rate = "66",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 12,
//                year = "1999",
//                title = "Family Guy",
//                synopsis = "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/gBGUL1UTUNmdRQT8gA1LUV4yg39.jpg",
//                trailer = "t3VtKdoPIYE",
//                rate = "65",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 13,
//                year = "1989",
//                title = "The Simpsons",
//                synopsis = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/yTZQkSsxUFJZJe67IenRM0AEklc.jpg",
//                trailer = "DX1iplQQJTo",
//                rate = "71",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 14,
//                year = "2005",
//                title = "Supernatural",
//                synopsis = "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/3iFm6Kz7iYoFaEcj4fLyZHAmTQA.jpg",
//                trailer = "yy96yJjkvjo",
//                rate = "73",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 15,
//                year = "2018",
//                title = "Titans",
//                synopsis = "A team of young superheroes led by Nightwing (formerly Batman's first Robin) form to combat evil and other perils.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/eeHI5iBSCOxj4HGSOmFM6azBmwb.jpg",
//                trailer = "QIYzdBAxcM8",
//                rate = "74",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 16,
//                year = "2014",
//                title = "The 100",
//                synopsis = "100 years in the future, when the Earth has been abandoned due to radioactivity, the last surviving humans live on an ark orbiting the planet — but the ark won't last forever. So the repressive regime picks 100 expendable juvenile delinquents to send down to Earth to see if the planet is still habitable.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/wBzNjurA8ijJPF21Ggs9nbviIzi.jpg",
//                trailer = "aDrsItJ_HU4",
//                rate = "65",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 17,
//                year = "2014",
//                title = "The Seven Deadly Sins",
//                synopsis = "The “Seven Deadly Sins”—a group of evil knights who conspired to overthrow the kingdom of Britannia—were said to have been eradicated by the Holy Knights, although some claim that they still live. Ten years later, the Holy Knights have staged a Coup d'état and assassinated the king, becoming the new, tyrannical rulers of the kingdom. Elizabeth, the king's only daughter, sets out on a journey to find the “Seven Deadly Sins,” and to enlist their help in taking back the kingdom.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/gxTojpKEOtue85EEFlozwRbDXwJ.jpg",
//                trailer = "Lwv2CQu2fYg",
//                rate = "66",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 18,
//                year = "2019",
//                title = "Carnival Row",
//                synopsis = "In a mystical and dark city filled with humans, fairies and other creatures, a police detective investigates a series of gruesome murders leveled against the fairy population. During his investigation, the detective becomes the prime suspect and must find the real killer to clear his name.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/3UupR0nS9R6Di9letdz4ftX95GF.jpg",
//                trailer = "369LHB9N-Ro",
//                rate = "76",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 19,
//                year = "2011",
//                title = "Game of Thrones",
//                synopsis = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
//                trailer = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
//                rate = "81",
//                category = CategoryEnum.TV.value
//            )
//        )
//        tvShow.add(
//            MovieEntity(
//                id = 20,
//                year = "2013",
//                title = "Marvel's Agents of S.H.I.E.L.D.",
//                synopsis = "Agent Phil Coulson of S.H.I.E.L.D. (Strategic Homeland Intervention, Enforcement and Logistics Division) puts together a team of agents to investigate the new, the strange and the unknown around the globe, protecting the ordinary from the extraordinary.",
//                poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/cXiETfFK1BTLest5fhTLfDLRdL6.jpg",
//                trailer = "T3T-evQZiQo",
//                rate = "68",
//                category = CategoryEnum.TV.value
//            )
//        )
//        return tvShow
//    }
//}