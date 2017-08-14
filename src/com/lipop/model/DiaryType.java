package com.lipop.model;

public class DiaryType {
		private int diaryTypeId;
		private String typeName;
		private int total;
		
		
		public DiaryType() {
			super();
		}

		public DiaryType(int diaryTypeId, String typeName) {
			super();
			this.diaryTypeId = diaryTypeId;
			this.typeName = typeName;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getDiaryTypeId() {
			return diaryTypeId;
		}
		public void setDiaryTypeId(int diaryTypeId) {
			this.diaryTypeId = diaryTypeId;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
}
