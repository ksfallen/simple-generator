-- t_address
select ST_ASTEXT(address_point),lat,lng, geohash from t_address;
-- point(lng, lat)
select st_pointfromgeohash(geohash,0),lat,lng, geohash from t_address;
select st_geohash(lng, lat, 12), st_geohash(address_point, 12) from t_address;

select ST_GEOMFROMTEXT(address_point) from t_address where addres_id = 1707;
SELECT ST_ASTEXT(ST_GEOMFROMTEXT(address_point)) from t_address where addres_id = 1707;
SELECT ST_ASTEXT(ST_PointFromGeoHash('wtq2yx8yfp3s', 0)) from t_address;


-- 查找10km内附近的人
SELECT a.*,
    (
            6371 * acos(
                        cos(radians(a.lat)) * cos(radians(30.230446)) * cos(
                                radians(120.149918) - radians(a.lng)
                        ) + sin(radians(a.lat)) * sin(radians(30.230446))
            )
        ) AS distance
FROM t_address a
# having distance < 10
ORDER BY distance;

SELECT
    *,
        GLength (
                LineStringFromWKB (
                        LineString (address_point, Point (120.149918,30.230446 ) )
                    )
            ) * 111.1 AS distance
FROM t_address
WHERE
    MBRContains (
            LineString (
                    Point ( 120.149918 + 200 / 111.1, 30.230446 + 200 / ( 111.1 / COS( RADIANS( 120.149918 ) ) )  ),
                    Point ( 120.149918 - 200 / 111.1, 30.230446 - 200 / ( 111.1 / COS( RADIANS( 120.149918 ) ) )  )
                ),
            address_point
        )
ORDER BY distance;
