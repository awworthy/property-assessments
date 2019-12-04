var map, heatmap, heatmapData, areaBoundary;

// Initialize and add the map
function initMap() {
    var initPoint = [new google.maps.LatLng(53.476383340913, -113.486365191502)];
    heatmapData = new google.maps.MVCArray(initPoint);
    heatmapData.clear();
    areaBoundary = new google.maps.MVCArray(initPoint);
    areaBoundary.clear();

    map = new google.maps.Map(
        document.getElementById('map'), {
            zoom: 11,
            disableDefaultUI:true,
            center: new google.maps.LatLng(53.5461,-113.4938),
            //Javascript map styling from https://developers.google.com/maps/documentation/javascript/styling
            styles: [
                {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
                {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
                {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
                {
                    "featureType": "landscape",
                    "elementType": "geometry",
                    "stylers": [{"color": "#000000"}]
                },
                {
                    "featureType": "administrative",
                    "elementType": "geometry",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "transit",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "road",
                    "elementType": "labels.icon",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    "featureType": "poi",
                    "stylers": [
                        {
                            "visibility": "off"
                        }
                    ]
                },
                {
                    featureType: 'administrative.locality',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#d59563'}]
                },
                {
                    featureType: 'poi',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#d59563'}]
                },
                {
                    featureType: 'poi.park',
                    elementType: 'geometry',
                    stylers: [{color: '#263c3f'}]
                },
                {
                    featureType: 'poi.park',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#6b9a76'}]
                },
                {
                    featureType: 'road',
                    elementType: 'geometry',
                    stylers: [{color: '#38414e'}]
                },
                {
                    featureType: 'road',
                    elementType: 'geometry.stroke',
                    stylers: [{color: '#212a37'}]
                },
                {
                    featureType: 'road',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#9ca5b3'}]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'geometry',
                    stylers: [{color: '#746855'}]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'geometry.stroke',
                    stylers: [{color: '#1f2835'}]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#f3d19c'}]
                },
                {
                    featureType: 'transit',
                    elementType: 'geometry',
                    stylers: [{color: '#2f3948'}]
                },
                {
                    featureType: 'transit.station',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#d59563'}]
                },
                {
                    featureType: 'water',
                    elementType: 'geometry',
                    stylers: [{color: '#17263c'}]
                },
                {
                    featureType: 'water',
                    elementType: 'labels.text.fill',
                    stylers: [{color: '#515c6d'}]
                },
                {
                    featureType: 'water',
                    elementType: 'labels.text.stroke',
                    stylers: [{color: '#17263c'}]
                }
            ]
        });

    //settings for the heatmap
    heatmap = new google.maps.visualization.HeatmapLayer({
        data: heatmapData,
        map: map
    });

    //heatmap gradient
    var gradient = [
        'rgba(0, 255, 255, 0)',
        'rgba(0, 255, 255, 1)',
        'rgba(0, 191, 255, 1)',
        'rgba(0, 127, 255, 1)',
        'rgba(0, 63, 255, 1)',
        'rgba(0, 0, 255, 1)',
        'rgba(0, 0, 223, 1)',
        'rgba(0, 0, 191, 1)',
        'rgba(0, 0, 159, 1)',
        'rgba(0, 0, 127, 1)',
        'rgba(63, 0, 91, 1)',
        'rgba(127, 0, 63, 1)',
        'rgba(191, 0, 31, 1)',
        'rgba(255, 0, 0, 1)'
    ];
    heatmap.set('gradient', gradient);
}

//Adds a data point to the map with a weight corresponding to the property value
function addProperties(properties){
    var latLng, value, weightedLocation;
    heatmapData.push(weightedLocation);
    for (p in properties) {
        latLng = new google.maps.LatLng(properties[p][0],properties[p][1]);
        value = properties[p][2];
        weightedLocation = {
            location: latLng,
            weight: value
        };
        heatmapData.push(weightedLocation);
    }
}

//Clears the map data
function clearMap(){
    heatmapData.clear();
    areaBoundary.clear();
    document.getElementById('max').innerHTML = "";
    document.getElementById('min').innerHTML = "";
}

//shea addition
function setCentreAndZoom(latitude, longitude, zoom){
    map.setCenter({lat:latitude, lng:longitude});
    map.setZoom(zoom);
}

//Render boundary around an area
function drawBoundary(boundary){
    var len = boundary.length;
    var latLng;
    for(var i=0; i < len; i++){
        latLng = new google.maps.LatLng(boundary[i][0],boundary[i][1]);
        areaBoundary.push(latLng);
    }

    var boundaryLine = new google.maps.Polyline({
        path: areaBoundary,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    boundaryLine.setMap(map);
}

//Update the legend range
function legend(max, min){
    document.getElementById('max').innerHTML = max;
    document.getElementById('min').innerHTML = min;

}

