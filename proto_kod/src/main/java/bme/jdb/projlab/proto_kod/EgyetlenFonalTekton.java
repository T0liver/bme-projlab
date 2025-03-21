package bme.jdb.projlab.proto_kod;

public class EgyetlenFonalTekton extends Tekton {

    /**
     * Gombafonál tektonra való átérését kezelő függvény, csak egy lehet a tektonon
     * 
     * @param melyik melyik gombafonál próbál áthidalni a tektonra
     */
    @Override
    public boolean fonalNo(GombaFonal melyik) {
        if (fonalak.size > 0) // ha van már nyilvántartott fonál a tektonon, nem lehet új
            return false;
        super.fonalNo(melyik); // ha ninxs, mehet, mint ha rendes lenne
    }

}
