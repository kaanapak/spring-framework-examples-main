

ymaps.ready(

    function () {

        var myMap = new ymaps.Map('map', {
            center: [55.751574, 37.573856],
            zoom: 9
        })
        var myGeoObjects = new ymaps.GeoObjectCollection({}, {
            preset: "islands#redCircleIcon",
            strokeWidth: 4,
            geodesic: true
        });




        for (let i = 0; i < Dialysis_coordinates.length; i++) {
            console.log(Dialysis_coordinates)
            var array;
            if (Dialysis_coordinates[i].length === 0) {
                array = new Array();
            } else {
                array = Dialysis_coordinates[i].replace(/, +/g, ",").split(",").map(Number);
            }
            var l = "[" + Dialysis_coordinates[i] + "]";

            var placemark = new ymaps.Placemark(array, {
                balloonContent: Dialysis_names[i]
            }, {
                // Setting the placemark style (circle).
                preset: "islands#circleDotIcon",
                // Setting the placemark color (in RGB format).
                iconColor: '#ff0000'
            });

            myGeoObjects.add(placemark);
// Adding the collection to the map.
        }


        for (let i = 0; i < Hospital_coordinates.length; i++) {
            var array;
            if (Hospital_coordinates[i].length === 0) {
                array = new Array();
            } else {
                array = Hospital_coordinates[i].replace(/, +/g, ",").split(",").map(Number);
            }
            var l = "[" + Hospital_coordinates[i] + "]";

            var placemark = new ymaps.Placemark(array, {
                balloonContent: Hospital_names[i]
            }, {
                // Setting the placemark style (circle).
                preset: "islands#circleDotIcon",
                // Setting the placemark color (in RGB format).
                iconColor: '#0000FF'
            });

            myGeoObjects.add(placemark);
// Adding the collection to the map.
        }


        myMap.geoObjects.add(myGeoObjects);
// Setting the map center and scale so that the whole collection is visible.
        myMap.setBounds(myGeoObjects.getBounds());
        });

