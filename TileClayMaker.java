
import net.minecraft.tileentity.TileEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TileClayMaker extends TileEntity implements ITickable {
	
	private int waterLVL = 0;
	private int time = 0;
	private boolean hasDirt = false;
	private boolean hasSand = false;
	private boolean success = null;
	
	public boolean addWater(int waterValue) {
		if(!worldObj.isRemote){
			if(waterLVL + waterValue < 41){
				waterLVL += waterValue; 
				return true;
			}
			return false
		}
	}
	
	public boolean addDirt(){
		if(hasDirt)
			return false;
		hasDirt = true;
		return true;
	}
	
	public boolean addSand(){
		if(hasSand)
			return false;
		hasSand = true;
		return true;
	}
	
	public void reset(){
		time = 0;
		waterLVL = 0;
		hasDirt = false;
		hasSand = false;
		success = null;	
	}
	
	public void spawn(String a){
		if(!worldObj.isRemote){
			if(a.equals("bucket")
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ITEMS.bucket)));
			if(a.equals("bottle")
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ITEMS.glass_bottle)));
		}
	}
	
	public void spawnResult(){
		if(!worldObj.isRemote){
			if(success != null){
				if(success)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(BLOCKS.Clay)));
				else
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(BLOCKS.Dirt)));
	}}}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("time", time);
		compound.setInteger("waterLVL", waterLVL);
		compound.setBoolean("hasDirt", hasDirt);
		compound.setBoolean("hasSand", hasSand);
		compound.setBoolean("success", success);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.time = compound.getInteger("time", time);
		this.waterLVL = compound.getInteger("waterLVL");
		this.hasDirt = compound.getBoolean("hasDirt");
		this.hasSand = compound.getBoolean("hasSand");
		this.success = compound.getBoolean("success");
	}
	
	
	public void claim(){
		spawnResult();
		reset();
	}
	
	@Override
	public void update() {
		if(!worldObj.isRemote){
			if(hasDirt && waterLVL <= 40)
				success = time > 200 && time < 260;
			if(hasSand && waterLVL <= 20)
				success = time > 100 && time < 160;
			if((hasDirt || hasSand) && time < Integer.MAX_INT)
				time++;
			//if(worldObj.isRaining()) 
			// addWater(1);
		}
	}
}