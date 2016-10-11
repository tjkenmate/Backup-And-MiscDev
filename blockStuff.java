##block commands (wrap to block);

@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileClayMaker){
			TileEntity clay = (TileClayMaker) tileEntity;
			if(heldItem.getItem() == Block.getItemFromBlock(BLOCKS.Dirt))
				if(clay.addDirt())
					//reduce stack
			else if(heldItem.getItem() == Block.getItemFromBlock(BLOCKS.Sand))
				if(clay.addSand())
					//reduce stack
			else if(heldItem.getItem() == ITEMS.water_bucket){
				if(clay.addWater(10)){
					//reduce stack
					clay.spawn("bucket");
				}
			}
			else if(heldItem.getItem() == ITEMS.water_bottle){
				if(clay.addWater(5)){
					//reduce stack
					clay.spawn("bottle");
				}
			}
			else 
				clay.claim();
		}
        return true;
    }