/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Toolspkg;

/**
 *
 * @author Dodd-pc
 */
public class PkgClass implements Comparable<Integer>{
   public int id;
   public String Name;
   public String moduleGoup;

    @Override
    public int compareTo(Integer o) {
        return o-this.id;
    }
}
