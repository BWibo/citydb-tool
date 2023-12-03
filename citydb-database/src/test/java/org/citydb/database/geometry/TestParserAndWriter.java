package org.citydb.database.geometry;

import org.citydb.model.geometry.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestParserAndWriter {
    String TEST_POINT_2D = "POINT (10.0 20.0)";
    String TEST_POINT_2D_B = "010100000000000000000024400000000000003440";

    String TEST_POINT_3D = "POINT Z (10.0 20.0 30.0)";
    String TEST_POINT_3D_B = "0101000080000000000000244000000000000034400000000000003E40";

    String TEST_MULTIPOINT_3D = "MULTIPOINT Z (10.0 20.0 30.0, 11.0 21.0 31.0)";
    String TEST_MULTIPOINT_3D_B = "0104000080020000000101000080000000000000244000000000000034400000000000003E400101000080000000000000264000000000000035400000000000003F40";

    String TEST_LINESTRING_3D = "LINESTRING Z (10.0 20.0 30.0, 11.0 21.0 31.0)";
    String TEST_LINESTRING_3D_B = "010200008002000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40";

    String TEST_MULTILINESTRING_3D = "MULTILINESTRING Z ((10.0 20.0 30.0, 11.0 21.0 31.0), (1.0 2.0 3.0, 2.0 3.0 4.0))";
    String TEST_MULTILINESTRING_3D_B = "010500008002000000010200008002000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40010200008002000000000000000000F03F00000000000000400000000000000840000000000000004000000000000008400000000000001040";

    String TEST_POLYGON_3D = "POLYGON Z ((10.0 20.0 30.0, 11.0 21.0 31.0, 12.0 22.0 32.0, 10.0 20.0 30.0))";
    String TEST_POLYGON_3D_B = "01030000800100000004000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40000000000000284000000000000036400000000000004040000000000000244000000000000034400000000000003E40";
    String TEST_POLYGON_3D_HOLE = "POLYGON Z ((10.0 20.0 30.0, 11.0 21.0 31.0, 12.0 22.0 32.0, 10.0 20.0 30.0), (1.0 2.0 3.0, 2.0 3.0 4.0, 3.0 4.0 5.0, 1.0 2.0 3.0))";
    String TEST_POLYGON_3D_HOLE_B = "01030000800200000004000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40000000000000284000000000000036400000000000004040000000000000244000000000000034400000000000003E4004000000000000000000F03F00000000000000400000000000000840000000000000004000000000000008400000000000001040000000000000084000000000000010400000000000001440000000000000F03F00000000000000400000000000000840";
    String TEST_MULTIPOLYGON_3D = "MULTIPOLYGON Z (((10.0 20.0 30.0, 11.0 21.0 31.0, 12.0 22.0 32.0, 10.0 20.0 30.0)), ((1.0 2.0 3.0, 2.0 3.0 4.0, 3.0 4.0 5.0, 1.0 2.0 3.0)))";
    String TEST_MULTIPOLYGON_3D_B = "01060000800200000001030000800100000004000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40000000000000284000000000000036400000000000004040000000000000244000000000000034400000000000003E4001030000800100000004000000000000000000F03F00000000000000400000000000000840000000000000004000000000000008400000000000001040000000000000084000000000000010400000000000001440000000000000F03F00000000000000400000000000000840";

    String TEST_POLYHEDRALSURFACE_3D = "POLYHEDRALSURFACE Z (((10.0 20.0 30.0, 11.0 21.0 31.0, 12.0 22.0 32.0, 10.0 20.0 30.0)), ((1.0 2.0 3.0, 2.0 3.0 4.0, 3.0 4.0 5.0, 1.0 2.0 3.0)))";
    String TEST_POLYHEDRALSURFACE_3D_B = "010F0000800200000001030000800100000004000000000000000000244000000000000034400000000000003E40000000000000264000000000000035400000000000003F40000000000000284000000000000036400000000000004040000000000000244000000000000034400000000000003E4001030000800100000004000000000000000000F03F00000000000000400000000000000840000000000000004000000000000008400000000000001040000000000000084000000000000010400000000000001440000000000000F03F00000000000000400000000000000840";

    String TEST_GEOMETRYCOLLECTION_3D = "GEOMETRYCOLLECTION Z (POLYHEDRALSURFACE Z (((1 1 1,2 2 2,3 3 3,1 1 1)),((1 1 1,2 2 2,3 3 3,1 1 1))),POLYHEDRALSURFACE Z (((1 1 1,2 2 2,3 3 3,1 1 1)),((1 1 1,2 2 2,3 3 3,1 1 1))))";
    String TEST_GEOMETRYCOLLECTION_3D_B = "010700008002000000010F0000800200000001030000800100000004000000000000000000F03F000000000000F03F000000000000F03F000000000000004000000000000000400000000000000040000000000000084000000000000008400000000000000840000000000000F03F000000000000F03F000000000000F03F01030000800100000004000000000000000000F03F000000000000F03F000000000000F03F000000000000004000000000000000400000000000000040000000000000084000000000000008400000000000000840000000000000F03F000000000000F03F000000000000F03F010F0000800200000001030000800100000004000000000000000000F03F000000000000F03F000000000000F03F000000000000004000000000000000400000000000000040000000000000084000000000000008400000000000000840000000000000F03F000000000000F03F000000000000F03F01030000800100000004000000000000000000F03F000000000000F03F000000000000F03F000000000000004000000000000000400000000000000040000000000000084000000000000008400000000000000840000000000000F03F000000000000F03F000000000000F03F";

    WKTParser wktParser = new WKTParser();
    WKBParser wkbParser = new WKBParser();
    WKTWriter wktWriter = new WKTWriter();
    WKBWriter wkbWriter = new WKBWriter();

    @Test
    @DisplayName("Test parse and write 2D Point")
    void testParse2DPoint() throws Throwable {
        Point point1 = (Point) wktParser.parse(TEST_POINT_2D);
        Point point2 = (Point) wkbParser.parse(TEST_POINT_2D_B);
        assertEquals(wktWriter.write(point1), TEST_POINT_2D);
        assertEquals(wktWriter.write(point2), TEST_POINT_2D);
        assertEquals(wkbWriter.write(point2), TEST_POINT_2D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D Point")
    void testParse3DPoint() throws Throwable {
        Point point1 = (Point) wktParser.parse(TEST_POINT_3D);
        Point point2 = (Point) wkbParser.parse(TEST_POINT_3D_B);
        assertEquals(wktWriter.write(point1), TEST_POINT_3D);
        assertEquals(wktWriter.write(point2), TEST_POINT_3D);
        assertEquals(wkbWriter.write(point2), TEST_POINT_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D LineString")
    void testParse3DLineString() throws Throwable {
        LineString lineString1 = (LineString) wktParser.parse(TEST_LINESTRING_3D);
        LineString lineString2 = (LineString) wkbParser.parse(TEST_LINESTRING_3D_B);
        assertEquals(wktWriter.write(lineString1), TEST_LINESTRING_3D);
        assertEquals(wktWriter.write(lineString2), TEST_LINESTRING_3D);
        assertEquals(wkbWriter.write(lineString2), TEST_LINESTRING_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D MultiLineString")
    void testParse3DMultiLineString() throws Throwable {
        MultiLineString multiLineString1 = (MultiLineString) wktParser.parse(TEST_MULTILINESTRING_3D);
        MultiLineString multiLineString2 = (MultiLineString) wkbParser.parse(TEST_MULTILINESTRING_3D_B);
        assertEquals(wktWriter.write(multiLineString1), TEST_MULTILINESTRING_3D);
        assertEquals(wktWriter.write(multiLineString2), TEST_MULTILINESTRING_3D);
        assertEquals(wkbWriter.write(multiLineString2), TEST_MULTILINESTRING_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D MultiPoint")
    void testParse3DMultiPoint() throws Throwable {
        MultiPoint multiPoint1 = (MultiPoint) wktParser.parse(TEST_MULTIPOINT_3D);
        MultiPoint multiPoint2 = (MultiPoint) wkbParser.parse(TEST_MULTIPOINT_3D_B);
        assertEquals(wktWriter.write(multiPoint1), TEST_MULTIPOINT_3D);
        assertEquals(wktWriter.write(multiPoint2), TEST_MULTIPOINT_3D);
        assertEquals(wkbWriter.write(multiPoint2), TEST_MULTIPOINT_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D Polygon")
    void testParse3DLPolygon() throws Throwable {
        Polygon polygon1 = (Polygon) wktParser.parse(TEST_POLYGON_3D);
        Polygon polygon2 = (Polygon) wkbParser.parse(TEST_POLYGON_3D_B);
        assertEquals(wktWriter.write(polygon1), TEST_POLYGON_3D);
        assertEquals(wktWriter.write(polygon2), TEST_POLYGON_3D);
        assertEquals(wkbWriter.write(polygon2), TEST_POLYGON_3D_B);

        polygon1 = (Polygon) wktParser.parse(TEST_POLYGON_3D_HOLE);
        polygon2 = (Polygon) wkbParser.parse(TEST_POLYGON_3D_HOLE_B);
        assertEquals(wktWriter.write(polygon1), TEST_POLYGON_3D_HOLE);
        assertEquals(wktWriter.write(polygon2), TEST_POLYGON_3D_HOLE);
        assertEquals(wkbWriter.write(polygon2), TEST_POLYGON_3D_HOLE_B);
    }

    @Test
    @DisplayName("Test parse and write 3D MultiPolygon")
    void testParse3DMultiPolygon() throws Throwable {
        MultiSurface multiSurface1 = (MultiSurface) wktParser.parse(TEST_MULTIPOLYGON_3D);
        MultiSurface multiSurface2 = (MultiSurface) wkbParser.parse(TEST_MULTIPOLYGON_3D_B);
        assertEquals(wktWriter.write(multiSurface1), TEST_MULTIPOLYGON_3D);
        assertEquals(wktWriter.write(multiSurface2), TEST_MULTIPOLYGON_3D);
        assertEquals(wkbWriter.write(multiSurface2), TEST_MULTIPOLYGON_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D PolyhedralSurface")
    void testParse3DPolyhedralSurface() throws Throwable {
        Solid solid1 = (Solid) wktParser.parse(TEST_POLYHEDRALSURFACE_3D);
        Solid solid2 = (Solid) wkbParser.parse(TEST_POLYHEDRALSURFACE_3D_B);
        assertEquals(wktWriter.write(Solid.of(solid1.getShell())), TEST_POLYHEDRALSURFACE_3D);
        assertEquals(wktWriter.write(Solid.of(solid2.getShell())), TEST_POLYHEDRALSURFACE_3D);
        assertEquals(wkbWriter.write(Solid.of(solid2.getShell())), TEST_POLYHEDRALSURFACE_3D_B);
    }

    @Test
    @DisplayName("Test parse and write 3D GeometryCollection(PolyhedralSurface)")
    void testParse3DGeometryCollection() throws Throwable {
        MultiSolid multiSolid1 = (MultiSolid) wktParser.parse(TEST_GEOMETRYCOLLECTION_3D);
        MultiSolid multiSolid2 = (MultiSolid) wkbParser.parse(TEST_GEOMETRYCOLLECTION_3D_B);
        assertEquals(multiSolid1.getVertexDimension(), 3);
        assertEquals(multiSolid1.getSolids().size(), 2);
        assertEquals(multiSolid2.getVertexDimension(), 3);
        assertEquals(multiSolid2.getSolids().size(), 2);
    }
}
